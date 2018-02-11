package tscheme.truffle.nodetypes.builtins.basicop;

import java.math.BigInteger;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function + specializations. Note that order of methods is important - long before BigInteger etc.!
 */
@NodeInfo(shortName = "+")
@GenerateNodeFactory
public abstract class AddBuiltInNode extends BuiltInNode {

    @Specialization(rewriteOn = ArithmeticException.class) // may be caused by Math.addExact(...)
    public long add(long left, long right) {
        //System.out.println(left + " + " + right);
        return Math.addExact(left, right);
    }

    @Specialization
    protected BigInteger add(BigInteger left, BigInteger right) {
        return left.add(right); // left.add(...) creates new BigInteger
    }

    @Specialization
    protected double add(double left, double right) {
        //System.out.println("double +");
        return (left + right);
    }

    @Specialization
    protected double add(double left, long right) {
        return (left + ((double) right));
    }

    @Specialization
    protected double add(double left, BigInteger right) {
        return (left + right.doubleValue());
    }

    @Specialization
    protected String add(String left, String right) {
        return left + right;
    }

    // Specializations below were replaced by Implicit cast.
    /*
    @Specialization
    protected String add(String left, long right) {
        return left + Long.toString(right,10);
    }

    @Specialization
    protected String add(String left, BigInteger right) {
        return left + right.toString(10);
    }

    @Specialization
    protected String add(String left, double right) {
        return left + Double.toString(right);
    }
    */
}
