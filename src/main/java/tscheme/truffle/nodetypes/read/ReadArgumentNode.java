package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

import tscheme.truffle.helpers.TSchemeException;
import tscheme.truffle.nodetypes.TSchemeNode;

public class ReadArgumentNode extends TSchemeNode {
    public final int argIndex;

    public ReadArgumentNode(int argumentIndex) {
        this.argIndex = argumentIndex;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        if (!this.isArgumentIndexInRange(virtualFrame, this.argIndex)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new TSchemeException("Missing argument: " + (this.argIndex + 1));
        }
        return this.getArgument(virtualFrame, this.argIndex);
    }

    @Override
    public String toString() {
        return "ReadArgNode: " + this.argIndex;
    }
}
