package tscheme.truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;


import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.nodetypes.builtins.BuiltInCreator;
import tscheme.truffle.parser.Namespace;

import java.util.HashMap;

public class Environment {
    private final FrameDescriptor globalFrameDescriptor;
    private final Namespace globalNamespace;
    private final MaterializedFrame globalFrame;

    public Environment() {
        this.globalFrameDescriptor = new FrameDescriptor();
        this.globalNamespace = new Namespace(this.globalFrameDescriptor);
        this.globalFrame = this.initGlobalFrame();

        //System.out.println("Global context: " + this.globalFrame);
    }

    private MaterializedFrame initGlobalFrame() {
        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(null,
                this.globalFrameDescriptor);

        addGlobalFunctions(frame);
        return frame.materialize();
    }

    private static void addGlobalFunctions(VirtualFrame virtualFrame) {

        FrameDescriptor frameDescriptor = virtualFrame.getFrameDescriptor();

        HashMap<String, TSchemeFunction> functions = BuiltInCreator.getAllBuiltIns();

        functions.forEach((k, v) -> virtualFrame.setObject(frameDescriptor.addFrameSlot(k),v));

        //System.out.println("Built-in methods in: " + virtualFrame.getFrameDescriptor());
    }

    public MaterializedFrame getGlobalFrame() {
        return this.globalFrame;
    }

    public Namespace getGlobalNamespace() {
        return this.globalNamespace;
    }
}
