// CheckStyle: start generated
package tscheme.truffle;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.math.BigInteger;

import tscheme.truffle.datatypes.TSchemeDataTypes;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

@GeneratedBy(TSchemeDataTypes.class)
public final class TSchemeDataTypesGen extends TSchemeDataTypes {

    @Deprecated public static final TSchemeDataTypesGen TSCHEMEDATATYPES = new TSchemeDataTypesGen();

    protected TSchemeDataTypesGen() {
    }

    public static boolean isLong(Object value) {
        return value instanceof Long;
    }

    public static long asLong(Object value) {
        assert value instanceof Long : "TSchemeDataTypesGen.asLong: long expected";
        return (long) value;
    }

    public static long expectLong(Object value) throws UnexpectedResultException {
        if (value instanceof Long) {
            return (long) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    public static boolean asBoolean(Object value) {
        assert value instanceof Boolean : "TSchemeDataTypesGen.asBoolean: boolean expected";
        return (boolean) value;
    }

    public static boolean expectBoolean(Object value) throws UnexpectedResultException {
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isBigInteger(Object value) {
        return value instanceof BigInteger;
    }

    public static BigInteger asBigInteger(Object value) {
        assert value instanceof BigInteger : "TSchemeDataTypesGen.asBigInteger: BigInteger expected";
        return (BigInteger) value;
    }

    public static BigInteger expectBigInteger(Object value) throws UnexpectedResultException {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isDouble(Object value) {
        return value instanceof Double;
    }

    public static double asDouble(Object value) {
        assert value instanceof Double : "TSchemeDataTypesGen.asDouble: double expected";
        return (double) value;
    }

    public static double expectDouble(Object value) throws UnexpectedResultException {
        if (value instanceof Double) {
            return (double) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isTSchemeFunction(Object value) {
        return value instanceof TSchemeFunction;
    }

    public static TSchemeFunction asTSchemeFunction(Object value) {
        assert value instanceof TSchemeFunction : "TSchemeDataTypesGen.asTSchemeFunction: TSchemeFunction expected";
        return (TSchemeFunction) value;
    }

    public static TSchemeFunction expectTSchemeFunction(Object value) throws UnexpectedResultException {
        if (value instanceof TSchemeFunction) {
            return (TSchemeFunction) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isTSchemeSymbol(Object value) {
        return value instanceof TSchemeSymbol;
    }

    public static TSchemeSymbol asTSchemeSymbol(Object value) {
        assert value instanceof TSchemeSymbol : "TSchemeDataTypesGen.asTSchemeSymbol: TSchemeSymbol expected";
        return (TSchemeSymbol) value;
    }

    public static TSchemeSymbol expectTSchemeSymbol(Object value) throws UnexpectedResultException {
        if (value instanceof TSchemeSymbol) {
            return (TSchemeSymbol) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isTSchemeList(Object value) {
        return value instanceof TSchemeList;
    }

    public static TSchemeList asTSchemeList(Object value) {
        assert value instanceof TSchemeList : "TSchemeDataTypesGen.asTSchemeList: TSchemeList expected";
        return (TSchemeList) value;
    }

    public static TSchemeList expectTSchemeList(Object value) throws UnexpectedResultException {
        if (value instanceof TSchemeList) {
            return (TSchemeList) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isString(Object value) {
        return value instanceof String;
    }

    public static String asString(Object value) {
        assert value instanceof String : "TSchemeDataTypesGen.asString: String expected";
        return (String) value;
    }

    public static String expectString(Object value) throws UnexpectedResultException {
        if (value instanceof String) {
            return (String) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static String expectImplicitString(int state, Object value) throws UnexpectedResultException {
        if ((state & 0b1) != 0 && value instanceof Long) {
            return castLongToString((long) value);
        } else if ((state & 0b10) != 0 && value instanceof Double) {
            return castDoubleToString((double) value);
        } else if ((state & 0b100) != 0 && value instanceof String) {
            return (String) value;
        } else if ((state & 0b1000) != 0 && value instanceof BigInteger) {
            return castBigIntegerToString((BigInteger) value);
        } else {
            throw new UnexpectedResultException(value);
        }
    }

    public static boolean isImplicitString(int state, Object value) {
        return ((state & 0b1) != 0 && value instanceof Long)
             || ((state & 0b10) != 0 && value instanceof Double)
             || ((state & 0b100) != 0 && value instanceof String)
             || ((state & 0b1000) != 0 && value instanceof BigInteger);
    }

    public static String asImplicitString(int state, Object value) {
        if ((state & 0b1) != 0 && value instanceof Long) {
            return castLongToString((long) value);
        } else if ((state & 0b10) != 0 && value instanceof Double) {
            return castDoubleToString((double) value);
        } else if ((state & 0b100) != 0 && value instanceof String) {
            return (String) value;
        } else if ((state & 0b1000) != 0 && value instanceof BigInteger) {
            return castBigIntegerToString((BigInteger) value);
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Illegal type ");
        }
    }

    public static int specializeImplicitString(Object value) {
        if (value instanceof Long) {
            return 0b1;
        } else if (value instanceof Double) {
            return 0b10;
        } else if (value instanceof String) {
            return 0b100;
        } else if (value instanceof BigInteger) {
            return 0b1000;
        } else {
            return 0;
        }
    }

    public static BigInteger expectImplicitBigInteger(int state, Object value) throws UnexpectedResultException {
        if ((state & 0b1) != 0 && value instanceof Long) {
            return castLongToBigInteger((long) value);
        } else if ((state & 0b10) != 0 && value instanceof BigInteger) {
            return (BigInteger) value;
        } else {
            throw new UnexpectedResultException(value);
        }
    }

    public static boolean isImplicitBigInteger(int state, Object value) {
        return ((state & 0b1) != 0 && value instanceof Long)
             || ((state & 0b10) != 0 && value instanceof BigInteger);
    }

    public static BigInteger asImplicitBigInteger(int state, Object value) {
        if ((state & 0b1) != 0 && value instanceof Long) {
            return castLongToBigInteger((long) value);
        } else if ((state & 0b10) != 0 && value instanceof BigInteger) {
            return (BigInteger) value;
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Illegal type ");
        }
    }

    public static int specializeImplicitBigInteger(Object value) {
        if (value instanceof Long) {
            return 0b1;
        } else if (value instanceof BigInteger) {
            return 0b10;
        } else {
            return 0;
        }
    }

}
