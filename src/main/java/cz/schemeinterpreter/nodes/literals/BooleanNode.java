package cz.schemeinterpreter.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;

public class BooleanNode extends SchemeNode {
    public final boolean value;

    public BooleanNode(boolean bool) {
        this.value = bool;
    }

    @Override
    public boolean executeBoolean(VirtualFrame virtualFrame) {
        return this.value;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.value;
    }

    @Override
    public String toString() {
        if (this.value) {
            return "#t";
        } else {
            return "#f";
        }
    }
}
