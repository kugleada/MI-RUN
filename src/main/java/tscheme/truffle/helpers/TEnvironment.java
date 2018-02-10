package tscheme.truffle.helpers;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class TEnvironment {

    private final FrameDescriptor baseFrameDescriptor;
    private final MaterializedFrame baseFrame;


    protected TEnvironment(FrameDescriptor baseFrameDescriptor,
                          MaterializedFrame baseFrame) {
        this.baseFrameDescriptor = baseFrameDescriptor;
        this.baseFrame = baseFrame;


        initializeBuiltins();
    }

    private void initializeBuiltins() {
        System.out.println("Initialized builtins for frame " + baseFrame + " with descriptor " + baseFrameDescriptor);
    }


    public FrameDescriptor getBaseFrameDescriptor() {
        return baseFrameDescriptor;
    }

    public MaterializedFrame getBaseFrame() {
        return baseFrame;
    }

    public static MaterializedFrame getDefaultFrame(FrameDescriptor frameDescriptor) {
        MaterializedFrame frame = Truffle.getRuntime().createMaterializedFrame(null, frameDescriptor);
        frame.setLong(frameDescriptor.findOrAddFrameSlot("GLOBAL_FRAME", FrameSlotKind.Long), 0);
        return frame;
    }

}