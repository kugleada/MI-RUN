package tscheme.truffle.nodetypes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.syntax.DoubleSyntax;

/**
 * Class for representing NODE of long (integer) in AST.
 */
public class LitDoubleNode extends TSchemeNode {

    public final double number; //< long value of this nodetypes.

    public LitDoubleNode(DoubleSyntax syntax) {

        this.number = syntax.getValue();

        setSourceSection(syntax.getSourceSection());
    }

    /**
     * String representation of long.
     * @return String representation of long.
     */
    @Override
    public String toString() {
        return Double.toString(this.number);
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly long).
     * @param virtualFrame If needed, can be read from.
     * @return String.
     */
    @Override
    public double executeDouble(VirtualFrame virtualFrame) {
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
