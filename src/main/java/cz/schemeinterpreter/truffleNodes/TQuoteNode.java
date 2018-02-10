package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

@NodeChild("literalNode")
@NodeField(name = "kind", type = TQuoteNode.QuoteKind.class)
public abstract class TQuoteNode extends TSchemeNode {
    public static enum QuoteKind {
        LONG,
        BOOLEAN,
        SYMBOL,
        LIST
    }

    protected abstract QuoteKind getKind();

    @Specialization(guards = "isLongKind()")
    protected long quoteLong(VirtualFrame virtualFrame, long value) {
        return value;
    }

    @Specialization(guards = "isBooleanKind()")
    protected boolean quoteBoolean(VirtualFrame virtualFrame, boolean value) {
        return value;
    }

    @Specialization(replaces = {"quoteLong", "quoteBoolean"})
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