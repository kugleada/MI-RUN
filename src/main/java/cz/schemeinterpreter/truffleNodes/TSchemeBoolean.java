package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

public class TSchemeBoolean extends TSchemeNode {
    public static final TSchemeBoolean TRUE = new TSchemeBoolean(Boolean.TRUE);
    public static final TSchemeBoolean FALSE = new TSchemeBoolean(Boolean.FALSE);

    private Boolean bool;
    public TSchemeBoolean(Boolean bool) {
        this.bool = bool;
    }

    @Override
    public boolean executeBoolean(VirtualFrame virtualFrame) {
        return this.bool;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.bool;
    }
}
