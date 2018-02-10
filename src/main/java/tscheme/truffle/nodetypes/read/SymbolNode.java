package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.frame.FrameSlot;

import tscheme.truffle.nodetypes.TSchemeNode;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class SymbolNode extends TSchemeNode {

    public abstract FrameSlot getSlot();

    @Override
    public String toString() {
        return "'" + this.getSlot().getIdentifier();
    }
}
