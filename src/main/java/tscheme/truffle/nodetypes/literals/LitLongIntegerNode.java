package tscheme.truffle.nodetypes.literals;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.syntax.LongIntegerSyntax;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of long (integer) in AST.
 */
public class LitLongIntegerNode extends TSchemeNode {

    public final long number; //< long value of this nodetypes.

    public LitLongIntegerNode(LongIntegerSyntax syntax) {

        this.number = syntax.getValue();
    }

    /**
     * String representation of long.
     * @return String representation of long.
     */
    @Override
    public String toString() {
        return Long.toString(this.number,10);
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly long).
     * @param virtualFrame If needed, can be read from.
     * @return String.
     */
    @Override
    public long executeLong(VirtualFrame virtualFrame) {
        return this.number;
    }

    /**
     * General execution of this nodetypes of AST, returns long, but as an Object.
     * @param virtualFrame If needed, can be read from.
     * @return long value, as an Object.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.number;
    }

}
