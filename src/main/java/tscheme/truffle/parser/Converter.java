package tscheme.truffle.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import tscheme.truffle.Environment;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.literals.*;
import tscheme.truffle.syntax.*;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;
import org.antlr.v4.runtime.misc.Pair;

import com.oracle.truffle.api.frame.FrameSlot;

import tscheme.truffle.helpers.TSchemeException;
import tscheme.truffle.nodetypes.invocations.InvokeNode;
import tscheme.truffle.nodetypes.read.ClosureSymbolNodeGen;
import tscheme.truffle.nodetypes.read.GlobalSymbolNodeGen;
import tscheme.truffle.nodetypes.read.LocalSymbolNodeGen;
import tscheme.truffle.nodetypes.read.SymbolNode;
import tscheme.truffle.nodetypes.controls.DefineNode;
import tscheme.truffle.nodetypes.controls.DefineNodeGen;
import tscheme.truffle.nodetypes.controls.IfNode;
import tscheme.truffle.nodetypes.controls.LambdaNode;
import tscheme.truffle.nodetypes.controls.LambdaNodeGen;
import tscheme.truffle.nodetypes.controls.QuoteNode;
import tscheme.truffle.nodetypes.controls.QuoteNodeGen;

/**
 * Convert Scheme syntax tree to AST.
 */
public class Converter {

    private Environment env; //< Environment.

    private Analyzer analyzer; //< Syntax checker

    public Converter() {
    }

    public TSchemeNode[] convertSyntaxToAST(Environment context, ListSyntax synExpressions) {

        this.env = context;

        Namespace fileNamespace = new Namespace(Namespace.TOP_NS,
                this.env.getGlobalNamespace());

        this.analyzer = new Analyzer(fileNamespace);

        this.analyzer.walk(synExpressions);

        // mistake here
        return StreamSupport.stream(synExpressions.getValue().spliterator(), false)
                .map(obj -> this.convert(obj, fileNamespace))
                .toArray(size -> new TSchemeNode[size]);
    }

    public TSchemeNode convert(Syntax<?> syntax, Namespace ns) {
        if (syntax instanceof LongIntegerSyntax) {
            return convert((LongIntegerSyntax) syntax);
        } else if (syntax instanceof BigIntegerSyntax) {
            return convert((BigIntegerSyntax) syntax);
        } else if (syntax instanceof DoubleSyntax) {
            return convert((DoubleSyntax) syntax);
        } else if (syntax instanceof BooleanSyntax) {
            return convert((BooleanSyntax) syntax);
        } else if (syntax instanceof StringSyntax) {
            return convert((StringSyntax) syntax);
        } else if (syntax instanceof SymbolSyntax) {
            return convert((SymbolSyntax) syntax, ns);
        } else if (syntax instanceof ListSyntax) {
            return convert((ListSyntax) syntax, ns);
        } else {
            throw new TSchemeException("Unknown datatypes: " + syntax.getClass());
        }
    }

    public static LitLongIntegerNode convert(LongIntegerSyntax number) {
        return new LitLongIntegerNode(number);
    }

    public static LitBigIntegerNode convert(BigIntegerSyntax number) {
        return new LitBigIntegerNode(number);
    }

    public static LitDoubleNode convert(DoubleSyntax number) {
        return new LitDoubleNode(number);
    }

    public static LitBooleanNode convert(BooleanSyntax bool) {
        return new LitBooleanNode(bool);
    }

    public static LitStringNode convert(StringSyntax str) {
        return new LitStringNode(str);
    }

    public SymbolNode convert(SymbolSyntax syntax, Namespace ns) {

    	SymbolNode node;

    	TSchemeSymbol sym = syntax.getValue();

        Pair<Integer, FrameSlot> pair = ns.getIdentifier(sym.name);


        if (pair.a == Namespace.LEVEL_UNDEFINED) {
            TSchemeReadException.throwReaderException(sym.name + " undefined", syntax, ns);
            return null;
        } else if (pair.a == 0) {
            node = LocalSymbolNodeGen.create(pair.b);
        } else if (pair.a == Namespace.LEVEL_GLOBAL) {
            node = GlobalSymbolNodeGen.create(pair.b, this.env.getGlobalFrame());
        } else {
            node = ClosureSymbolNodeGen.create(pair.b, pair.a);
        }

        return node;
    }

    public TSchemeNode convert(ListSyntax syntax, Namespace ns) {

        TSchemeList<? extends Syntax<? extends Object>> list = syntax.getValue();
        if (list == TSchemeList.EMPTY || list.size() == 0) {
            return new LitListNode(TSchemeList.EMPTY);
        }

        Syntax<? extends Object> car = list.car();
        if (car instanceof SymbolSyntax) {
            TSchemeSymbol sym = ((SymbolSyntax) car).getValue();
            switch (sym.name) {
            case "define":
                return convertDefine(syntax, ns);
            case "lambda":
                return convertLambda(syntax, ns);
            case "if":
                return convertIf(syntax, ns);
            case "quote":
                return convertQuote(syntax, ns);
            }
        }
        return convertInvoke(list, ns);
    }

    private InvokeNode convertInvoke(TSchemeList<? extends Syntax<? extends Object>> list,
                                     Namespace ns) {

        TSchemeNode functionNode = convert(list.car(), ns);

        TSchemeNode[] arguments = StreamSupport
                .stream(list.cdr().spliterator(), false)
                .map(syn-> convert(syn, ns))
                .toArray(size -> new TSchemeNode[size]);

        return new InvokeNode(functionNode, arguments);

    }

    private DefineNode convertDefine(ListSyntax syntax, Namespace ns) {
        TSchemeList<? extends Syntax<? extends Object>> list = syntax.getValue();
        SymbolSyntax symSyntax = (SymbolSyntax) list.cdr().car();
        FrameSlot nameSlot = ns.getIdentifier(symSyntax.getValue().name).b;
        TSchemeNode valueNode = convert(list.cdr().cdr().car(), ns);
        DefineNode node = DefineNodeGen.create(valueNode, nameSlot);
        if (valueNode instanceof LambdaNode) {
            // TODO : not good enough. if there's an error in the lambda,
            // the name won't be used. Have to pass name
            LambdaNode lambda = (LambdaNode) valueNode;
            lambda.setName(nameSlot.toString());
        }
        return node;
    }

    @SuppressWarnings("unchecked")
    private LambdaNode convertLambda(
            ListSyntax syntax,
            Namespace ns)
    {
    	TSchemeList<? extends Syntax<? extends Object>> list = syntax.getValue();
        Namespace lambdaNs = this.analyzer.getNamespace(syntax);
        List<FrameSlot> formalParameters = new ArrayList<>();
        ListSyntax argsSyntax = (ListSyntax) list.cdr().car();
        for (SymbolSyntax arg : (TSchemeList<SymbolSyntax>) argsSyntax.getValue()) {
            formalParameters.add(convert(arg, lambdaNs).getSlot());
        }
        List<TSchemeNode> bodyNodes = new ArrayList<>();
        for (Syntax<? extends Object> body : list.cdr().cdr()) {
            bodyNodes.add(convert(body, lambdaNs));
        }

        TSchemeFunction function = new TSchemeFunction(
                formalParameters.toArray(new FrameSlot[] {}),
                bodyNodes.toArray(new TSchemeNode[] {}),
                lambdaNs.getFrameDescriptor());
        LambdaNode node = LambdaNodeGen.create(function);
        return node;
    }

    private IfNode convertIf(ListSyntax syntax, Namespace ns) {
    	TSchemeList<? extends Syntax<? extends Object>> list = syntax.getValue();
        return new IfNode(convert(list.cdr().car(), ns),
                convert(list.cdr().cdr().car(), ns),
                convert(list.cdr().cdr().cdr().car(), ns));
    }

    private static QuoteNode convertQuote(ListSyntax syntax,
                                          Namespace ns) {
        TSchemeList<? extends Syntax<? extends Object>> list = syntax.getValue();
        Syntax<? extends Object> value = list.cdr().car();
        TSchemeNode node;
        QuoteNode.QuoteKind kind;
        if (value instanceof LongIntegerSyntax) {
            kind = QuoteNode.QuoteKind.LONG;
            node = convert((LongIntegerSyntax) value);
        } else if (value instanceof BooleanSyntax) {
            kind = QuoteNode.QuoteKind.BOOLEAN;
            node = convert((BooleanSyntax) value);
        } else if (value instanceof DoubleSyntax) {
            kind = QuoteNode.QuoteKind.DOUBLE;
            node = convert((DoubleSyntax) value);
        } else if (value instanceof StringSyntax) {
            kind = QuoteNode.QuoteKind.STRING;
            node = new LitStringNode((StringSyntax) value);
        } else if (value instanceof SymbolSyntax) {
            kind = QuoteNode.QuoteKind.SYMBOL;
            node = new LitSymbolNode((SymbolSyntax) value);
        } else if (value instanceof ListSyntax) {
            kind = QuoteNode.QuoteKind.LIST;
            node = new LitListNode(((TSchemeList<?>) value.strip()));
        } else {
            throw new TSchemeException("Unknown quote datatypes: " +
                    value.getClass());
        }
        return QuoteNodeGen.create(node, kind);
    }
}
