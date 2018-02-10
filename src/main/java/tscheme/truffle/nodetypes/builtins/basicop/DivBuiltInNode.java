package tscheme.truffle.nodetypes.builtins.basicop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function / (division) specializations. Note that order of methods is important - long before BigInteger etc.!
 */
@NodeInfo(shortName = "/")
@GenerateNodeFactory
public abstract class DivBuiltInNode extends BuiltInNode {

    @Specialization
    protected long divide(long left, long right) {
        return (left / right); // may throw Arithmetic Exception if right is zero!
        // return value is automatically typed back to long, not double
    }

    @Specialization
    protected BigInteger divide(BigInteger left, BigInteger right) {
        return left.divide(right); // may throw Arithmetic Exception if right is zero!
    }

    @Specialization
    protected double divide(double left, double right) {
        return (left / right); // may throw Arithmetic Exception if right is zero!
    }
}
