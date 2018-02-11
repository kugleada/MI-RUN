package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Specialized class for local variable storage.
 */
public abstract class LocalSymbolNode extends SymbolNode {

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected long readLong(VirtualFrame frame)
            throws FrameSlotTypeException{
        return frame.getLong(getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readBoolean(VirtualFrame frame)
            throws FrameSlotTypeException{
        return frame.getBoolean(getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readDouble(VirtualFrame frame)
            throws FrameSlotTypeException{
        return frame.getBoolean(getSlot());
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected Object readObject(VirtualFrame frame)
            throws FrameSlotTypeException{
        return frame.getObject(getSlot());
    }

    @Specialization(replaces = { "readLong", "readBoolean", "readDouble", "readObject" })
    protected Object read(VirtualFrame frame) {
        return frame.getValue(getSlot());
    }
}
