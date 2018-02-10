package cz.schemeinterpreter.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.types.SchemeList;

public class LiteralListNode extends SchemeNode {
    private final SchemeList<?> list;

    public LiteralListNode(SchemeList<?> list) {
        this.list = list;
    }

    @Override
    public SchemeList<?> executeSchemeList(VirtualFrame virtualFrame) {
        return this.list;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
