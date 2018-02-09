package cz.schemeinterpreter.nodes.special;


import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;

public class QuoteNode extends SchemeNode {
    private SchemeNode literalNode;

    public QuoteNode(SchemeNode literalNode) {
        this.literalNode = literalNode;
    }

    public Object execute(VirtualFrame virtualFrame) {
        return literalNode;
    }
}

/*
@NodeChild("literalNode")
@NodeField(name = "kind", type = QuoteNode.QuoteKind.class)
public abstract class QuoteNode extends SchemeNode {
    public static enum QuoteKind {
        LONG,
        BOOLEAN,
        SYMBOL,
        LIST
    }

    protected abstract QuoteKind getKind();

    @Specialization(guards = "isLongKind")
    protected long quoteLong(VirtualFrame virtualFrame, long value) {
        return value;
    }

    @Specialization(guards = "isBooleanKind")
    protected boolean quoteBoolean(VirtualFrame virtualFrame, boolean value) {
        return value;
    }

    @Specialization(contains = {"quoteLong", "quoteBoolean"})
    protected Object quote(VirtualFrame virtualFrame, Object value) {
        return value;
    }

    protected boolean isLongKind() {
        return this.getKind() == QuoteKind.LONG;
    }

    protected boolean isBooleanKind() {
        return this.getKind() == QuoteKind.BOOLEAN;
    }
}
*/
