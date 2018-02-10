package tscheme.truffle.nodetypes.literals;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.syntax.BooleanSyntax;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of Boolean in AST.
 */
public class LitBooleanNode extends TSchemeNode {

    public final boolean value; //< Boolean value of this nodetypes.

    /**
     * Create Boolean nodetypes for AST.
     * @param bool Boolean value that was read from source, to be inserted into AST.
     */
    public LitBooleanNode(BooleanSyntax bool) {

        this.value = bool.getValue();

        setSourceSection(bool.getSourceSection());
    }

    /**
     * String representation.
     * @return String representation of boolean.
     */
    @Override
    public String toString() {
        if (this.value) return "#t";
        return "#f";
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly boolean).
     * @param virtualFrame If needed, can be read from.
     * @return Boolean value.
     */
    @Override
    public boolean executeBoolean(VirtualFrame virtualFrame) {
        return this.value;
    }

    /**
     * General execution of this nodetypes of AST, returns Boolean, but as Object.
     * @param virtualFrame If needed, can be read from.
     * @return Boolean value, but as Object.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.value;
    }

}
