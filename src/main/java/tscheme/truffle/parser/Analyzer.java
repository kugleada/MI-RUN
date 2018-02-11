package tscheme.truffle.parser;

import java.util.HashMap;
import java.util.Map;

import tscheme.truffle.syntax.ListSyntax;
import tscheme.truffle.syntax.SymbolSyntax;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

/**
 * Class serves for syntax check, i.e. does define have two arguments?
 * This class walks through the syntax objects to define all the namespaces
 * and the identifiers within. Special forms are also verified to be structured
 * correctly.
 */
public class Analyzer extends SyntaxExpressionListener {

    private final Map<ListSyntax, Namespace> namespaces;

    private Namespace currentNamespace;

    public Analyzer(Namespace topNamespace) {
        this.namespaces = new HashMap<>();
        this.namespaces.put(null, topNamespace);
        this.currentNamespace = topNamespace;
    }

    public Namespace getNamespace(ListSyntax syntax) {
        return this.namespaces.get(syntax);
    }

    @Override
    public void onDefine(ListSyntax syntax) {
        TSchemeList<? extends Syntax<?>> list = syntax.getValue();

        if (list.size() != 3) {
            TSchemeReadException.throwReaderException("Analyzer: (define symbol value)", syntax,
                    this.currentNamespace);
        }
        if (!(list.cdr().car() instanceof SymbolSyntax)) {
            TSchemeReadException.throwReaderException("Analyzer: Define first argument must be a symbol",
                    syntax, this.currentNamespace);
        }
        TSchemeSymbol sym = (TSchemeSymbol) list.cdr().car().getValue();
        this.currentNamespace.addIdentifier(sym.name);
    }

    @Override
    public void onLambda(ListSyntax syntax) {
        TSchemeList<? extends Syntax<?>> list = syntax.getValue();
        if (list.size() < 3) {
            TSchemeReadException.throwReaderException("Analyzer: (lambda (params ..) (body) (body) (...))",
                    syntax, this.currentNamespace);
        }

        if (!(list.cdr().car() instanceof ListSyntax)) {
            TSchemeReadException.throwReaderException("Analyzer: Lambda second argument must be a list",
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
            TSchemeReadException.throwReaderException("Analyzer: (if (test) (then) (else)",
                    syntax, this.currentNamespace);
        }
    }

    @Override
    public void onQuote(ListSyntax syntax) {
        if (syntax.getValue().size() != 2) {
            TSchemeReadException.throwReaderException("Analyzer: Quote has only one argument: ", syntax,
                    this.currentNamespace);
        }
    }

    private void addLambdaArguments(ListSyntax argsListSyntax) {
        for (Syntax<?> syntax: argsListSyntax.getValue()) {
            if (syntax instanceof SymbolSyntax) {
                this.currentNamespace.addIdentifier(
                        ((SymbolSyntax) syntax).getValue().name);
            } else {
                TSchemeReadException.throwReaderException("Analyzer: Lambda argument must be a symbol: ",
                        argsListSyntax, this.currentNamespace);
            }
        }
    }
}
