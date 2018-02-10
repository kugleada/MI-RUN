package tscheme.truffle.parser;

import tscheme.truffle.syntax.ListSyntax;
import tscheme.truffle.syntax.SymbolSyntax;
import tscheme.truffle.datatypes.TSchemeList;

/**
 * A simple walk through the syntax objects that is created by {@link Reader}.
 * Override the on- methods to be alerted when different types are encountered.
 * Convenience methods for controls forms are included.
 *
 */
public abstract class SexpListener {
    private static enum ListType {
        LIST,
        DEFINE,
        LAMBDA,
        IF,
        QUOTE;
    }

    public void walk(Syntax<?> sexp) {

        if (sexp instanceof ListSyntax) {

            ListSyntax list = (ListSyntax) sexp;

            ListType listType = ListType.LIST;

            if (isListOf("define", list)) {
                onDefine(list);
                listType = ListType.DEFINE;
            } else if (isListOf("lambda", list)) {
                onLambda(list);
                listType = ListType.LAMBDA;
            } else if (isListOf("if", list)) {
                onIf(list);
                listType = ListType.IF;
            } else if (isListOf("quote", list)) {
                onQuote(list);
                listType = ListType.QUOTE;
            } else {
                onList(list);
            }

            for (Syntax<?> sub : list.getValue()) {
                walk(sub);
            }

            switch (listType) {
            case DEFINE:
                onDefineExit(list);
                break;
            case LAMBDA:
                onLambdaExit(list);
                break;
            case IF:
                onIfExit(list);
                break;
            case QUOTE:
                onQuoteExit(list);
                break;
            case LIST:
                onListExit(list);
                break;
            }
        } else if (sexp instanceof SymbolSyntax) {
            onSymbol((SymbolSyntax) sexp);
        }
    }

    private boolean isListOf(String name, ListSyntax listSyntax) {
        TSchemeList<? extends Syntax<?>> list = listSyntax.getValue();
        return list != TSchemeList.EMPTY && list.car() != null
                && list.car() instanceof SymbolSyntax
                && name.equals(((SymbolSyntax) list.car()).getValue().name);
    }

    public void onList(ListSyntax syntax) {return;}

    public void onListExit(ListSyntax syntax) {}

    public void onDefine(ListSyntax syntax) {}

    public void onDefineExit(ListSyntax syntax) {}

    public void onLambda(ListSyntax syntax) {}

    public void onLambdaExit(ListSyntax syntax) {}

    public void onIf(ListSyntax syntax) {}

    public void onIfExit(ListSyntax syntax) {}

    public void onQuote(ListSyntax syntax) {}

    public void onQuoteExit(ListSyntax syntax) {}

    public void onSymbol(SymbolSyntax syntax) {}
}
