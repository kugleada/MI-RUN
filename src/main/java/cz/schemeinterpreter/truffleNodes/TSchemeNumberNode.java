package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

public class TSchemeNumberNode extends TSchemeNode {
    public final long num;

    public TSchemeNumberNode(long number) {
        this.num = number;
    }

    @Override
    public long executeLong(VirtualFrame virtualFrame) {
        return this.num;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.num;
    }

    @Override
    public String toString() {
        return "" + this.num;
    }
}
