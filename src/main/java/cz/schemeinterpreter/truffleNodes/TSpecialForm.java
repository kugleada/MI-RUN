package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.frame.FrameSlot;
import cz.schemeinterpreter.datatypes.SchemeNode;
import cz.schemeinterpreter.scheme_env.TEnvironment;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import cz.schemeinterpreter.truffleNodes.type.TSchemeList;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;
import cz.schemeinterpreter.truffleNodes.TDefineNodeGen;

public abstract class TSpecialForm extends SchemeNode {
    private static final TSchemeSymbol DEFINE = new TSchemeSymbol("define");
    private static final TSchemeSymbol LAMBDA = new TSchemeSymbol("lambda");
    private static final TSchemeSymbol IF = new TSchemeSymbol("if");
    private static final TSchemeSymbol QUOTE = new TSchemeSymbol("quote");

    protected TSchemeList listNode;

    public TSpecialForm(TSchemeList listNode) {
        this.listNode = listNode;
    }

    public static TSchemeNode check(TSchemeList l,
                                    TEnvironment tge) {
        System.out.println("VOLAM s " + l.car());
        if (l == TSchemeList.EMPTY) {
            System.out.println("PRAZDNE");
            return l;
        } else if (l.car().equals(DEFINE)) {
            //System.out.println("Správně tu: " + l.cdr().car().toString());
            FrameSlot slot = tge.getBaseFrameDescriptor().findOrAddFrameSlot(l.cdr().car().toString());
            return TDefineNodeGen.create(l.cdr().cdr().car(), slot);
        } else if (l.car().equals(LAMBDA)) {
            return null; // new LambdaSpecialForm(l);
        } else if (l.car().equals(IF)) {
            return null; //new IfSpecialForm(l);
        } else if (l.car().equals(QUOTE)) {
            return null; // new QuoteSpecialForm(l);
        }
        return l;
    }
}
