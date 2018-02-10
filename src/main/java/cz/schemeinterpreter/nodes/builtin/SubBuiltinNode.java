package cz.schemeinterpreter.nodes.builtin;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.math.BigInteger;

@NodeInfo(shortName = "-")
@GenerateNodeFactory
public abstract class SubBuiltinNode extends BuiltinNode {

    @Specialization
    protected BigInteger add(BigInteger value0, BigInteger value1) {
        return value0.subtract(value1);
    }
}