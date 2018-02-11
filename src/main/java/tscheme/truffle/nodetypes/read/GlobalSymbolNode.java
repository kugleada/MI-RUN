package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Global storage, with specialized methods.
 */
@NodeField(name = "globalFrame", type = MaterializedFrame.class)
public abstract class GlobalSymbolNode extends SymbolNode {

    public abstract MaterializedFrame getGlobalFrame(); //< Materialized needed

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected long readLong(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.getGlobalFrame().getLong(this.getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readBoolean(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.getGlobalFrame().getBoolean(this.getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readDouble(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.getGlobalFrame().getBoolean(this.getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected Object readObject(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.getGlobalFrame().getObject(this.getSlot());
    }

    @Specialization(replaces = { "readLong", "readBoolean", "readDouble", "readObject", }) // Illegal type
    protected Object read(VirtualFrame frame) {
        return this.getGlobalFrame().getValue(this.getSlot());
    }
}
