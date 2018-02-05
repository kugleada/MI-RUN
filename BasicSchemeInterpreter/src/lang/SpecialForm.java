package lang;

import datatypes.Node;
import datatypes.SchemeList;
import datatypes.SchemeSymbol;

public abstract class SpecialForm extends Node {
    private static final SchemeSymbol DEFINE = new SchemeSymbol("define");
    private static final SchemeSymbol LAMBDA = new SchemeSymbol("lambda");
    private static final SchemeSymbol IF = new SchemeSymbol("if");
    private static final SchemeSymbol QUOTE = new SchemeSymbol("quote");

    protected SchemeList listNode;

    public SpecialForm(SchemeList listNode) {
        this.listNode = listNode;
    }

    public static Node check(SchemeList l) {

        if (l == SchemeList.EMPTY) {
            return l;
        } else if (l.car().equals(DEFINE)) {
            return new DefineSpecialForm(l);
        } else if (l.car().equals(LAMBDA)) {
            return new LambdaSpecialForm(l);
        } else if (l.car().equals(IF)) {
            return new IfSpecialForm(l);
        } else if (l.car().equals(QUOTE)) {
            return new QuoteSpecialForm(l);
        }
        return l;
    }
}
