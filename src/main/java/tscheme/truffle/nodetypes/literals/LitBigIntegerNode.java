package tscheme.truffle.nodetypes.literals;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.parser.syntax.BigIntegerSyntax;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of BigInteger in AST.
 */
public class LitBigIntegerNode extends TSchemeNode {

    public final BigInteger value; //< value of this number

    /**
     * Create BitInteger nodetypes for AST.
     * @param number Number read from source to be inserted into AST.
     */
    public LitBigIntegerNode(BigIntegerSyntax number) {
        this.value = number.getValue();
    }

    /**
     * String representation.
     * @return String representation of number.
     */
    @Override
    public String toString() {
        return this.value.toString();
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly BigInteger).
     * @param virtualFrame If needed, can be read from.
     * @return BigInteger value.
     */
    @Override
    public BigInteger executeBigInteger(VirtualFrame virtualFrame) {
        return this.value;
    }

    /**
     * General execution of this nodetypes of AST, returns BigInteger, but as Object.
     * @param virtualFrame If needed, can be read from.
     * @return BigInteger value, but as Object.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.value;
    }

}
