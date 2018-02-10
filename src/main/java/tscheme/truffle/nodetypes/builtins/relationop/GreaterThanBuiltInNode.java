package tscheme.truffle.nodetypes.builtins.relationop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function for > (greater than) operation, returns true if left arg is greater than left arg.
 */
@NodeInfo(shortName=">")
@GenerateNodeFactory
public abstract class GreaterThanBuiltInNode extends BuiltInNode {

    @Specialization
    protected boolean greaterThan(long left, long right) {
        return left > right;
    }

    @Specialization
    protected boolean greaterThan(BigInteger left, BigInteger right) {
        return left.compareTo(right) > 0;
    }

    @Specialization
    protected boolean greaterThan(double left, double right) {
        return left > right;
    }
}
