package tscheme.truffle.helpers;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class TEnvironmentBuilder {
    private FrameDescriptor baseFrameDescriptor = new FrameDescriptor();
    private MaterializedFrame baseFrame;

    public TEnvironmentBuilder setBaseFrameDescriptor(FrameDescriptor baseFrameDescriptor) {
        this.baseFrameDescriptor = baseFrameDescriptor;
        return this;
    }

    public TEnvironmentBuilder setBaseFrame(MaterializedFrame baseFrame) {
        this.baseFrame = baseFrame;
        return this;
    }

    public TEnvironment createEnvironment() {
        if (baseFrame == null) baseFrame = TEnvironment.getDefaultFrame(baseFrameDescriptor);
        return new TEnvironment(baseFrameDescriptor, baseFrame);
    }
}