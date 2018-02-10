package tscheme.truffle.nodetypes.builtins.basicop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function * (modulus) specializations. Note that order of methods is important - long before BigInteger etc.!
 */
@NodeInfo(shortName = "*")
@GenerateNodeFactory
public abstract class MulBuiltInNode extends BuiltInNode {

    @Specialization(rewriteOn = ArithmeticException.class) // rewriteOn long is not enough
    protected long multiply(long left, long right) {
        return Math.multiplyExact(left, right);
    }

    @Specialization
    protected BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Specialization
    protected double multiply(double left, double right) {
        return (left * right); // careful about NaN
    }
}
