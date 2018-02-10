package tscheme.truffle.nodetypes.builtins.relationop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function for < (less than) operation, returns true if left arguments is lower than right argument.
 */
@NodeInfo(shortName="<")
@GenerateNodeFactory
public abstract class LessThanBuiltInNode extends BuiltInNode {

    @Specialization
    protected boolean lessThan(long left, long right) {
        return left < right;
    }

    @Specialization
    protected boolean lessThan(BigInteger left, BigInteger right) {
        return left.compareTo(right) < 0;
    }

    @Specialization
    protected boolean lessThan(double left, double right) {
        return left < right;
    }
}
