package cz.schemeinterpreter.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class ReadArgumentNode extends SchemeNode {
    private final int argumentIndex;

    public ReadArgumentNode(int argumentIndex) {
        this.argumentIndex = argumentIndex;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        System.out.println("executing ReadArgumentNode" + argumentIndex);
        if (!this.isArgumentIndexInRange(virtualFrame)) {
            throw new IllegalArgumentException("Not enough arguments passed");
        }
        return this.getArgument(virtualFrame);
    }

    protected boolean isArgumentIndexInRange(VirtualFrame virtualFrame) {
        return (argumentIndex + 1) < virtualFrame.getArguments().length;
    }

    protected Object getArgument(VirtualFrame virtualFrame) {
        System.out.println("returned arg: " + virtualFrame.getArguments()[argumentIndex + 1]);
        return virtualFrame.getArguments()[argumentIndex + 1];
    }
}
