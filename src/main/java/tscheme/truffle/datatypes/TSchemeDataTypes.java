package tscheme.truffle.datatypes;

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
    public static BigInteger castLongToBigInteger(long value) {
        return BigInteger.valueOf(value);
    }

    @ImplicitCast // implicit cast from long to String
    public static String castLongToString(long value) {
        return Long.toString(value,10);
    }

    @ImplicitCast // implicit cast from BigInteger to String
    public static String castBigIntegerToString(BigInteger value) {
        return value.toString(10);
    }

    @ImplicitCast // implicit cast from Double to String
    public static String castDoubleToString(double value) {
        return Double.toString(value);
    }

    /*
    @ImplicitCast // implicit cast from long to double, possible loss of precision
    public static double castLongToDouble(long value) {
        return ((double) value);
    }
    */

    /* Sometimes causes problems with converting large enough BigInteger into double, causing it to reach Infinity.
    @ImplicitCast // implicit cast from BigInteger to double, possible loss of precision
    public static double castBigIntegerToDouble(BigInteger value) {
        return (value.doubleValue());
    }
    */
}
