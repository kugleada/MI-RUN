package datatypes;

import scheme_env.Environment;

import java.math.BigInteger;

public class SchemeNumber extends SchemeNode {
    private final BigInteger num;
    public SchemeNumber(BigInteger num) {
        this.num = num;
    }

    @Override
    public Object eval(Environment env) {
        return this.num;
    }

    @Override
    public String toString() {
        return num.toString();
    }
}
