package tscheme.truffle.nodetypes.literals;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.syntax.StringSyntax;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of String in AST.
 */
public class LitStringNode extends TSchemeNode {

    public final String string; //< String value of this nodetypes.

    /**
     * Create String nodetypes for AST.
     * @param string String read from source to be inserted into AST.
     */
    public LitStringNode(StringSyntax string) {

        this.string = string.getValue(); // get value of string that was read
    }

    /**
     * String representation.
     * @return String representation.
     */
    @Override
    public String toString() {
        return this.string;
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly String).
     * @param virtualFrame If needed, can be read from.
     * @return String.
     */
    @Override
    public String executeString(VirtualFrame virtualFrame) {
        return this.string;
    }

    /**
     * General execution of this nodetypes of AST, returns String, but as an Object.
     * @param virtualFrame If needed, can be read from.
     * @return String value, as an Object.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.string;
    }

}
