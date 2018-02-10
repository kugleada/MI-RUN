package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;

public class TSchemeLiteralSymbolNode extends TSchemeNode {
    public final TSchemeSymbol symbol;

    public TSchemeLiteralSymbolNode(TSchemeSymbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public TSchemeSymbol executeTSchemeSymbol(VirtualFrame virtualFrame) {
        return this.symbol;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.symbol;
    }

    @Override
    public String toString() {
        return this.symbol.toString();
    }
}
