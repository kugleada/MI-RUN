package tscheme.truffle.nodetypes.literals;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of List in AST.
 */
public class LitListNode extends TSchemeNode {

    public final TSchemeList<?> list; //< list to be represented

    /**
     * Constructs list literal.
     * @param list
     */
    public LitListNode(TSchemeList<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public TSchemeList<?> executeTSchemeList(VirtualFrame virtualFrame) {
        return this.list;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.list;
    }

}
