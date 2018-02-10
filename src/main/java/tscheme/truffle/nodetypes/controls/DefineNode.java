package tscheme.truffle.nodetypes.controls;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

import tscheme.truffle.nodetypes.TSchemeNode;

@NodeChild("valueNode")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class DefineNode extends TSchemeNode {

	protected abstract FrameSlot getSlot(); // get slot for THIS node

	protected abstract Node getValueNode(); // get VALUE of THIS slot

	@Override
	public String toString() {
		return "DefineNode: (define " + this.getSlot().getIdentifier() + " "
				+ this.getValueNode() + ")";
	}

	// Below are specializations for datatypes - long, boolean, double and general Object (String etc.)
	// |
	// |
	// V

	@Specialization
	protected long writeLong(VirtualFrame virtualFrame, long value) {
		//System.out.println("Writing long into frame...");
		//System.out.println("Writing into frame: " + virtualFrame);
		//System.out.println("Writing into slot: " + this.getSlot());
		getSlot().setKind(FrameSlotKind.Long);
		virtualFrame.setLong(getSlot(), value);
		//try{virtualFrame.getLong(getSlot());} catch (Exception e) {System.out.println("???");}
		//System.out.println("Wrote into frame: " + virtualFrame);
		//System.out.println("Current frame: " + Truffle.getRuntime().getCurrentFrame());
		return value;
	}

	@Specialization
	protected boolean writeBoolean(VirtualFrame virtualFrame, boolean value) {
		//System.out.println("Writing boolean into frame...");
		getSlot().setKind(FrameSlotKind.Boolean);
		virtualFrame.setBoolean(this.getSlot(), value);
		return value;
	}

	@Specialization
	protected double writeDouble(VirtualFrame virtualFrame, double value) {
		//System.out.println("Writing double into frame...");
		getSlot().setKind(FrameSlotKind.Double);
		virtualFrame.setDouble(getSlot(), value);
		return value;
	}

	@Specialization(replaces = {"writeLong", "writeBoolean", "writeDouble"})
	protected Object write(VirtualFrame virtualFrame, Object value) {
		//System.out.println("Writing Object/String/Function into frame...");
		getSlot().setKind(FrameSlotKind.Object);
		virtualFrame.setObject(getSlot(), value);
		return value;
	}
}

