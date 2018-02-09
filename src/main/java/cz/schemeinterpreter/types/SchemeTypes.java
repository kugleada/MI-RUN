package cz.schemeinterpreter.types;

import com.oracle.truffle.api.dsl.TypeSystem;

import java.math.BigInteger;

@TypeSystem({boolean.class, BigInteger.class, SchemeFunction.class, SchemeSymbol.class, SchemeList.class})
public abstract class SchemeTypes {

}
