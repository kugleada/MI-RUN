package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.dsl.TypeSystem;
import cz.schemeinterpreter.truffleNodes.type.TSchemeFunction;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;

@TypeSystem({long.class,boolean.class, TSchemeFunction.class, TSchemeSymbol.class})
public abstract class TSchemeTypes {
}
