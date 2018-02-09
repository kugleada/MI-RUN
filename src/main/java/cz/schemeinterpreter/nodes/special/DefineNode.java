package cz.schemeinterpreter.nodes.special;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;

public class DefineNode extends SchemeNode {
    private SchemeNode valueNode;
    private FrameSlot slot;

    public DefineNode(SchemeNode valueNode, FrameSlot slot) {
        this.valueNode = valueNode;
        this.slot = slot;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        Object value = valueNode.execute(virtualFrame);
        virtualFrame.setObject(slot, value);
        return value;
    }

    //protected abstract FrameSlot getSlot();


}

/*
@NodeChild("valueNode")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class DefineNode extends MumblerNode {
    protected abstract FrameSlot getSlot();

    @Specialization(guards = "isLongKind")
    protected long writeLong(VirtualFrame virtualFrame, long value) {
        virtualFrame.setLong(this.getSlot(), value);
        return value;
    }

    @Specialization(guards = "isBooleanKind")
    protected boolean writeBoolean(VirtualFrame virtualFrame, boolean value) {
        virtualFrame.setBoolean(this.getSlot(), value);
        return value;
    }

    @Specialization(contains = {"writeLong", "writeBoolean"})
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
*/