// CheckStyle: start generated
package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.schemeinterpreter.truffleNodes.type.TSchemeFunction;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;

@GeneratedBy(TSchemeTypes.class)
public final class TSchemeTypesGen extends TSchemeTypes {

    @Deprecated public static final TSchemeTypesGen TSCHEMETYPES = new TSchemeTypesGen();

    protected TSchemeTypesGen() {
    }

    public static boolean isLong(Object value) {
        return value instanceof Long;
    }

    public static long asLong(Object value) {
        assert value instanceof Long : "TSchemeTypesGen.asLong: long expected";
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
        assert value instanceof Boolean : "TSchemeTypesGen.asBoolean: boolean expected";
        return (boolean) value;
    }

    public static boolean expectBoolean(Object value) throws UnexpectedResultException {
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isTSchemeFunction(Object value) {
        return value instanceof TSchemeFunction;
    }

    public static TSchemeFunction asTSchemeFunction(Object value) {
        assert value instanceof TSchemeFunction : "TSchemeTypesGen.asTSchemeFunction: TSchemeFunction expected";
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
        assert value instanceof TSchemeSymbol : "TSchemeTypesGen.asTSchemeSymbol: TSchemeSymbol expected";
        return (TSchemeSymbol) value;
    }

    public static TSchemeSymbol expectTSchemeSymbol(Object value) throws UnexpectedResultException {
        if (value instanceof TSchemeSymbol) {
            return (TSchemeSymbol) value;
        }
        throw new UnexpectedResultException(value);
    }

}
