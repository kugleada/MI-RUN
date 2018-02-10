package tscheme.truffle.nodetypes.builtins.basicop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function % (modulus) specializations. Note that order of methods is important - long before BigInteger etc.!
 */
@NodeInfo(shortName = "%")
@GenerateNodeFactory
public abstract class ModBuiltInNode extends BuiltInNode {

    @Specialization
    protected long modulo(long left, long right) {
        return (left % right); // may throw ArithmeticException if right <= 0
    }

    @Specialization
    protected BigInteger modulo(BigInteger left, BigInteger right) {
        return left.mod(right); // may throw ArithmeticException if right <= 0
    }
}
