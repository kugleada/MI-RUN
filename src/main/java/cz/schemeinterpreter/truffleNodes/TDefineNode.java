package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

@NodeChild("variableValueNode")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class TDefineNode extends TSchemeNode {
    protected abstract FrameSlot getSlot();

    @Specialization(guards = "isLongKind()")
    protected long writeLong(VirtualFrame virtualFrame, long value) {
        virtualFrame.setLong(this.getSlot(), value);
        return value;
    }

    @Specialization(guards = "isBooleanKind()")
    protected boolean writeBoolean(VirtualFrame virtualFrame, boolean value) {
        // getSlot().setKind(FrameSlotKind.Boolean); // in case of dire need
        virtualFrame.setBoolean(this.getSlot(), value);
        return value;
    }

    @Specialization(replaces = {"writeLong", "writeBoolean"})
    protected Object write(VirtualFrame virtualFrame, Object value) {
        FrameSlot slot = this.getSlot();
        if (slot.getKind() != FrameSlotKind.Object) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            slot.setKind(FrameSlotKind.Object);
        }
        virtualFrame.setObject(slot, value);
        return value;
    }

    protected boolean isLongKind() {
        return this.getSlot().getKind() == FrameSlotKind.Long;
    }

    protected boolean isBooleanKind() {
        return this.getSlot().getKind() == FrameSlotKind.Boolean;
    }
}