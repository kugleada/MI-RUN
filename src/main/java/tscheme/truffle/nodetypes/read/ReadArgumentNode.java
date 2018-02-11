package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

import tscheme.truffle.helpers.TSchemeException;
import tscheme.truffle.nodetypes.TSchemeNode;

/**
 * Reading function arguments.
 */
public class ReadArgumentNode extends TSchemeNode {
    public final int argIndex;

    public ReadArgumentNode(int argumentIndex) {
        this.argIndex = argumentIndex;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        // Check if it is in the frame.
        if (!this.isArgumentIndexInRange(frame, this.argIndex)) {

            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new TSchemeException("Missing argument: " + (this.argIndex));
        }
        // Return argument.
        return this.getArgument(frame, this.argIndex);
    }

    @Override
    public String toString() {
        return "ReadArgNode: " + this.argIndex;
    }
}
