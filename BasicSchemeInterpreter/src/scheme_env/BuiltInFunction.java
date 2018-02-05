package scheme_env;

import datatypes.SchemeFunction;
import datatypes.SchemeList;

import java.math.BigInteger;

abstract class BuiltInFunction extends SchemeFunction {

    protected final String name;

    public BuiltInFunction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "<procedure: " + this.name;
    }
    static final SchemeFunction EQUALS = new BuiltInFunction("equals") {
        @Override
        public Object apply(Object... args) {
            BigInteger last = (BigInteger) args[0];
            for (Object arg : args) {
                BigInteger current = (BigInteger) arg;
                if (!last.equals(current)) {
                    return false;
                } else {
                    last = current;
                }
            }
            return true;
        }
    };

    static final SchemeFunction LESS_THAN = new BuiltInFunction("less-than") {
        @Override
        public Object apply(Object... args) {
            assert args.length > 1;
            BigInteger num = (BigInteger) args[args.length - 1];
            for (int i=args.length - 2; i>=0; i--) {
                BigInteger n = (BigInteger) args[i];
                if (n.compareTo(num) >= 0) {
                    return false;
                }
                num = n;
            }
            return true;
        }
    };

    static final SchemeFunction GREATER_THAN = new BuiltInFunction("greater-than") {
        @Override
        public Object apply(Object... args) {
            assert args.length > 1;
            BigInteger num = (BigInteger) args[args.length - 1];
            for (int i=args.length - 2; i>=0; i--) {
                BigInteger n = (BigInteger) args[i];
                if (n.compareTo(num) <= 0) {
                    return false;
                }
                num = n;
            }
            return true;
        }
    };

    static final SchemeFunction DIV = new BuiltInFunction("div") {
        @Override
        public Object apply(Object... args) {
            if (args.length == 1) {
                return BigInteger.ONE.divide((BigInteger) args[0]);
            }
            BigInteger quotient = (BigInteger) args[0];
            for (int i=1; i<args.length; i++) {
                quotient = quotient.divide((BigInteger) args[i]);
            }
            return quotient;
        }
    };

    static final SchemeFunction MULT = new BuiltInFunction("mult") {
        @Override
        public Object apply(Object... args) {
            BigInteger product = BigInteger.ONE;
            for (Object arg : args) {
                product = product.multiply((BigInteger) arg);
            }
            return product;
        }
    };

    static final SchemeFunction MINUS = new BuiltInFunction("minus") {
        @Override
        public Object apply(Object... args) {
            if (args.length < 1) {
                throw new RuntimeException(this.name + " requires an argument");
            }
            switch (args.length) {
                case 1:
                    return ((BigInteger) args[0]).negate();
                default:
                    BigInteger diff = (BigInteger) args[0];
                    for (int i=1; i<args.length; i++) {
                        diff = diff.subtract((BigInteger) args[i]);
                    }
                    return diff;
            }
        }
    };

    static final SchemeFunction PLUS = new BuiltInFunction("plus") {
        @Override
        public Object apply(Object... args) {
            BigInteger sum = BigInteger.ZERO;
            for (Object arg : args) {
                sum = sum.add((BigInteger) arg);
            }
            return sum;
        }
    };
    /*
    static final SchemeFunction LIST = new BuiltInFunction("list") {
        @Override
        public Object apply(Object... args) {
            return SchemeList.list(args);
        }
    };
    */
    static final SchemeFunction CAR = new BuiltInFunction("car") {
        @Override
        public Object apply(Object... args) {
            assert args.length == 1;
            return ((SchemeList) args[0]).car();
        }
    };

    static final SchemeFunction CDR = new BuiltInFunction("cdr") {
        @Override
        public Object apply(Object... args) {
            assert args.length == 1;
            return ((SchemeList) args[0]).cdr();
        }
    };

    static final SchemeFunction PRINTLN = new BuiltInFunction("println") {
        @Override
        public Object apply(Object... args) {
            for (Object arg : args) {
                System.out.println(arg);
            }
            return SchemeList.EMPTY;
        }
    };

}