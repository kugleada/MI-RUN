package cz.schemeinterpreter.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReadArgumentNode extends SchemeNode {
    private final int argumentIndex;

    public ReadArgumentNode(int argumentIndex) {
        this.argumentIndex = argumentIndex;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        if (!this.isArgumentIndexInRange(virtualFrame)) {
            throw new IllegalArgumentException("Not enough arguments passed");
        }
        return this.getArgument(virtualFrame);
    }

    protected boolean isArgumentIndexInRange(VirtualFrame virtualFrame) {
        return (argumentIndex + 1) < virtualFrame.getArguments().length;
    }

    protected Object getArgument(VirtualFrame virtualFrame) {
        return virtualFrame.getArguments()[argumentIndex + 1];
    }
}
