package tscheme.truffle.nodetypes.literals;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.syntax.SymbolSyntax;
import tscheme.truffle.datatypes.TSchemeSymbol;

import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Class for representing NODE of Symbol in AST.
 */
public class LitSymbolNode extends TSchemeNode {
    public final TSchemeSymbol symbol; //< Symbol value of this nodetypes.

    /**
     * Create a literals symbol from this nodetypes.
     * @param symbol Symbol value that was read from source, to be inserted into AST.
     */
    public LitSymbolNode(SymbolSyntax symbol) {
        this.symbol = symbol.getValue();
        setSourceSection(symbol.getSourceSection());
    }

    /**
     * String representation of symbol.
     * @return String representation of symbol.
     */
    @Override
    public String toString() {
        return "'" + this.symbol.toString();
    }

    /**
     * Execute this nodetypes of AST, without boxing, specialized (return directly Symbol).
     * @param virtualFrame If needed, can be read from.
     * @return Symbol.
     */
    @Override
    public TSchemeSymbol executeTSchemeSymbol(VirtualFrame virtualFrame) {
        return this.symbol;
    }

    /**
     * General execution of this nodetypes of AST, returns Symbol, but as an Object.
     * @param virtualFrame If needed, can be read from.
     * @return Symbol, as an Object.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return this.symbol;
    }

}
