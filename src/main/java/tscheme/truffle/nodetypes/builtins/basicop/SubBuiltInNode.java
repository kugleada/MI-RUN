package tscheme.truffle.nodetypes.builtins.basicop;

import com.oracle.truffle.api.nodes.NodeInfo;
import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;

import java.math.BigInteger;

/**
 * Built-in function - (subtract) specializations. Note that order of methods is important - long before BigInteger etc.!
 */
@NodeInfo(shortName = "-")
@GenerateNodeFactory
public abstract class SubBuiltInNode extends BuiltInNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long minus(long left, long right) {
        return (left - right);
    }

    @Specialization
    protected BigInteger minus(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Specialization
    protected double minus(double left, double right) {
        return (left - right);
    }

    @Specialization
    protected double minus(double left, long right) {
        return (left - ((double) right));
    }

    @Specialization
    protected double minus(double left, BigInteger right) {
        return (left - right.doubleValue());
    }
}
