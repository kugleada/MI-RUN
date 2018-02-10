package tscheme.truffle.parser;

import java.util.HashMap;
import java.util.Map;

import tscheme.truffle.syntax.ListSyntax;
import tscheme.truffle.syntax.SymbolSyntax;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

import com.oracle.truffle.api.frame.FrameDescriptor;

/**
 * This class walks through the syntax objects to define all the namespaces
 * and the identifiers within. Special forms are also verified to be structured
 * correctly.
 * <p>
 * After the scan, a lambda's namespace can be fetched to find symbols'
 * appropriate {@link FrameDescriptor}.
 * <p>
 * The following controls form properties are checked
 * <ol>
 * <li> <code>define</code> calls only contain 2 arguments.
 * <li> The first argument to <code>define</code> is a symbol.
 * <li> <code>lambda</code> calls have at least 3 arguments.
 * <li> The first argument to <code>lambda</code> must be a list of symbols.
 * <li> <code>if</calls> have exactly 3 arguments.
 * <li> <code>quote</code> quote calls have exactly 1 argument.
 * </ol>
 */
public class Analyzer extends SexpListener {
    private final Map<ListSyntax, Namespace> namespaces;
    private Namespace currentNamespace;

    public Analyzer(Namespace topNamespace) {
        this.namespaces = new HashMap<>();
        this.namespaces.put(null, topNamespace);
        this.currentNamespace = topNamespace;
    }

    public Map<ListSyntax, Namespace> getNamespaceMap() {
        return this.namespaces;
    }

    public Namespace getNamespace(ListSyntax syntax) {
        return this.namespaces.get(syntax);
    }

    @Override
    public void onDefine(ListSyntax syntax) {
        TSchemeList<? extends Syntax<?>> list = syntax.getValue();

        if (list.size() != 3) {
            TSchemeReadException.throwReaderException("(define symbol value)", syntax,
                    this.currentNamespace);
        }
        if (!(list.cdr().car() instanceof SymbolSyntax)) {
            TSchemeReadException.throwReaderException("define first argument must be a symbol",
                    syntax, this.currentNamespace);
        }
        TSchemeSymbol sym = (TSchemeSymbol) list.cdr().car().getValue();
        this.currentNamespace.addIdentifier(sym.name);
    }

    @Override
    public void onLambda(ListSyntax syntax) {
        TSchemeList<? extends Syntax<?>> list = syntax.getValue();
        if (list.size() < 3) {
            TSchemeReadException.throwReaderException("(lambda (params ..) (body))",
                    syntax, this.currentNamespace);
        }

        if (!(list.cdr().car() instanceof ListSyntax)) {
            TSchemeReadException.throwReaderException("lambda second argument must be a list",
                    syntax, this.currentNamespace);;
        }

        this.currentNamespace = new Namespace(syntax.getName(), this.currentNamespace);
        this.namespaces.put(syntax, this.currentNamespace);
        this.addLambdaArguments((ListSyntax) list.cdr().car());
    }

    @Override
    public void onLambdaExit(ListSyntax syntax) {
        this.currentNamespace = this.currentNamespace.getParent();
    }

    @Override
    public void onIf(ListSyntax syntax) {
        if (syntax.getValue().size() != 4) {
            TSchemeReadException.throwReaderException("(if (test) (then) (else)",
                    syntax, this.currentNamespace);
        }
    }

    @Override
    public void onQuote(ListSyntax syntax) {
        if (syntax.getValue().size() != 2) {
            TSchemeReadException.throwReaderException("quote has only one argument", syntax,
                    this.currentNamespace);
        }
    }

    private void addLambdaArguments(ListSyntax argsListSyntax) {
        for (Syntax<?> syntax: argsListSyntax.getValue()) {
            if (syntax instanceof SymbolSyntax) {
                this.currentNamespace.addIdentifier(
                        ((SymbolSyntax) syntax).getValue().name);
            } else {
                TSchemeReadException.throwReaderException("lambda argument must be a symbol",
                        argsListSyntax, this.currentNamespace);
            }
        }
    }
}
