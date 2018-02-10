package cz.schemeinterpreter.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.schemeinterpreter.types.SchemeFunction;
import cz.schemeinterpreter.types.SchemeList;
import cz.schemeinterpreter.types.SchemeSymbol;
import cz.schemeinterpreter.types.SchemeTypesGen;

import java.math.BigInteger;

@NodeInfo(language = "Scheme Language", description = "The abstract base node for all expressions")
public abstract class SchemeNode extends Node {
    public abstract Object execute(VirtualFrame virtualFrame);

    public BigInteger executeBigInteger(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return SchemeTypesGen.expectBigInteger(
                this.execute(virtualFrame));
    }

    public boolean executeBoolean(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return SchemeTypesGen.expectBoolean(
                this.execute(virtualFrame));
    }

    public SchemeSymbol executeSchemeSymbol(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return SchemeTypesGen.expectSchemeSymbol(
                this.execute(virtualFrame));
    }

    public SchemeFunction executeSchemeFunction(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return SchemeTypesGen.expectSchemeFunction(
                this.execute(virtualFrame));
    }

    public SchemeList<?> executeSchemeList(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return SchemeTypesGen.expectSchemeList(
                this.execute(virtualFrame));
    }

}
