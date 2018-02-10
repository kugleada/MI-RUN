package cz.schemeinterpreter.truffleNodes.node;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.schemeinterpreter.truffleNodes.TSchemeTypesGen;
import cz.schemeinterpreter.truffleNodes.type.TSchemeFunction;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;

@NodeInfo(language = "Scheme Language", description = "Abstract node types, base class for all nodes.")
public abstract class TSchemeNode extends Node {
    public abstract Object execute(VirtualFrame virtualFrame);

    public long executeLong(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeTypesGen.expectLong(
                this.execute(virtualFrame));
    }

    public boolean executeBoolean(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeTypesGen.expectBoolean(
                this.execute(virtualFrame));
    }

    public TSchemeSymbol executeTSchemeSymbol(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeTypesGen.expectTSchemeSymbol(
                this.execute(virtualFrame));
    }

    public TSchemeFunction executeTSchemeFunction(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeTypesGen.expectTSchemeFunction(
                this.execute(virtualFrame));
    }

}
