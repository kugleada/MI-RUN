package tscheme.truffle.nodetypes.controls;

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

	// What can be quoted - pretty much all types from TSchemeSchemeDataTypes except for functions etc.
	public enum QuoteKind {
		LONG,
		BOOLEAN,
        DOUBLE,
		STRING,
		SYMBOL,
		LIST
	}

	@Specialization
	protected long quoteLong(VirtualFrame virtualFrame, long value) {
		return value;
	}

	@Specialization
	protected boolean quoteBoolean(VirtualFrame virtualFrame, boolean value) {
		return value;
	}

	@Specialization
	protected double quoteDouble(VirtualFrame virtualFrame, double value) {
		return value;
	}

	@Specialization(replaces = {"quoteLong", "quoteBoolean", "quoteDouble"})
	protected Object quote(VirtualFrame virtualFrame, Object value) {
		return value;
	}
}

