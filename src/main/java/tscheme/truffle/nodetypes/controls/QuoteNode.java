package tscheme.truffle.nodetypes.controls;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.controls.QuoteNode.QuoteKind;

/**
 * Represents Quote node in AST.
 */
@NodeChild("literal")
@NodeField(name = "kind", type = QuoteKind.class)
public abstract class QuoteNode extends TSchemeNode {
	protected abstract QuoteKind getKind(); //< get kind for THIS node

	// What can be quoted - pretty much all types from TSchemeSchemeDataTypes except for functions etc.
	public enum QuoteKind {
		LONG,
		BOOLEAN,
        DOUBLE,
		STRING,
		SYMBOL,
		LIST
	}

	@Specialization(guards = "isLongKind()")
	protected long quoteLong(VirtualFrame virtualFrame, long value) {
		return value;
	}

	@Specialization(guards = "isBooleanKind()")
	protected boolean quoteBoolean(VirtualFrame virtualFrame, boolean value) {
		return value;
	}

	@Specialization(guards = "isDoubleKind()")
	protected double quoteDouble(VirtualFrame virtualFrame, double value) {
		return value;
	}

	@Specialization(replaces = {"quoteLong", "quoteBoolean", "quoteDouble"})
	protected Object quote(VirtualFrame virtualFrame, Object value) {
		return value;
	}
	/*
	Below are Kind - checkers for this slot.
	I.e. we are not trying to put double into boolean slot.
	 */
	protected boolean isLongKind() {
		return (this.getKind() == QuoteKind.LONG);
	}

	protected boolean isBooleanKind() {
		return (this.getKind()  == QuoteKind.BOOLEAN);
	}

	protected boolean isDoubleKind() {
		return (this.getKind()  == QuoteKind.DOUBLE);
	}


}

