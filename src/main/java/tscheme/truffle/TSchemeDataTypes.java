package tscheme.truffle;

import java.math.BigInteger;

import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({long.class, // integer / long
        boolean.class, // boolean
        BigInteger.class, // BigInteger for large numbers
        double.class, // double, floating-point numbers
        TSchemeFunction.class, // function
        TSchemeSymbol.class, // symbol
        TSchemeList.class, // list
        String.class}) // string, array of characters
public class TSchemeDataTypes { // datatypes definition for our language

    @ImplicitCast // implicit cast from long to BigInteger
    public static BigInteger castBigInteger(long value) {
        return BigInteger.valueOf(value);
    }

    @ImplicitCast // implicit cast from long to double, possible loss of precision
    public static double castLongToDouble(long value) {
        return ((double) value);
    }

    @ImplicitCast // implicit cast from BigInteger to double, possible loss of precision
    public static double castBigIntegerToDouble(BigInteger value) {
        return (value.doubleValue());
    }

}
