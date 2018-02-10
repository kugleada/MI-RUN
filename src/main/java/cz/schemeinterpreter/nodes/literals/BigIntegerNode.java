package cz.schemeinterpreter.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;

import java.math.BigInteger;

public class BigIntegerNode extends SchemeNode {
    private final BigInteger number;

    public BigIntegerNode(BigInteger number) {
        this.number = number;
    }

    @Override
    public BigInteger executeBigInteger(VirtualFrame virtualFrame) {
        return this.number;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.number;
    }

    @Override
    public String toString() {
        return this.number.toString();
    }
}
