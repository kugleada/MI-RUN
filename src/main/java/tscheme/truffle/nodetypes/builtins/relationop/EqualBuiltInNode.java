package tscheme.truffle.nodetypes.builtins.relationop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function for = (equals) operation, returns true if arguments are the same.
 */
@NodeInfo(shortName = "=")
@GenerateNodeFactory
public abstract class EqualBuiltInNode extends BuiltInNode {

    @Specialization
    protected boolean equals(boolean left, boolean right) {
        return (left == right);
    }

    @Specialization
    protected boolean equals(long left, long right) {
        return (left == right);
    }

    @Specialization
    protected boolean equals(BigInteger left, BigInteger right) {
        return left.equals(right);
    }

    @Specialization
    protected boolean equals(double left, double right) {
        return (left == right);
    }

    @Specialization
    protected boolean equals(TSchemeList<?> left, TSchemeList<?> right) {
        return left.equals(right);
    }

    @Specialization
    protected boolean equals(Object left, Object right) {
        return left.equals(right);
    }
}
