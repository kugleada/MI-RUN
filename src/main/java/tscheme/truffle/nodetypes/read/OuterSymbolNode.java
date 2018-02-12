package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

/**
 * Specialized class for outer variable storage, with specializations.
 * Depth indicates how deep in Frame stack is desired variable defined.
 */
@NodeField(name = "depth", type = int.class)
public abstract class OuterSymbolNode extends SymbolNode {

    // Getter for closure slots.
    public interface FrameGet<DataType> {
        DataType get(Frame frame, FrameSlot slot) throws FrameSlotTypeException;
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected long readLong(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.readUpFrameStack(Frame::getLong, frame);
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected boolean readBoolean(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.readUpFrameStack(Frame::getBoolean, frame);
    }

    @Specialization(rewriteOn = FrameSlotTypeException.class)
    protected Object readObject(VirtualFrame frame)
            throws FrameSlotTypeException {
        return this.readUpFrameStack(Frame::getObject, frame);
    }

    @Specialization(replaces = { "readLong", "readBoolean", "readObject" })
    protected Object read(VirtualFrame frame) {
        try {
            return this.readUpFrameStack(Frame::getValue, frame);
        } catch (FrameSlotTypeException e) {
        }
        return null;
    }

    public abstract int getDepth();

    //find variable in frame stack
    @ExplodeLoop
    public <DataType> DataType readUpFrameStack(FrameGet<DataType> getter, Frame frame)
            throws FrameSlotTypeException {

        Frame lookupFrame = frame;
        for (int i = 0; i < this.getDepth(); i++) {
            lookupFrame = getLexicalScope(lookupFrame);
        }
        return getter.get(lookupFrame, this.getSlot());
    }
}
