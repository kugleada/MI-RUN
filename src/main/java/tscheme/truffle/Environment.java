package tscheme.truffle;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;


import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.nodetypes.builtins.BuiltInCreator;
import tscheme.truffle.parser.Namespace;

import java.util.HashMap;

/**
 * Class representing global environment. 
 */
public class Environment {
    private final FrameDescriptor globalFrameDescriptor; //< frame descriptor for frame
    private final MaterializedFrame globalFrame; //< materialized frame (on heap)
    
    private final Namespace globalNamespace; //< namespace, esp. for functions and variables
    

    public Environment() {
        // create new descriptor and initialize frame
        this.globalFrameDescriptor = new FrameDescriptor();
        this.globalFrame = this.initGlobalFrame();

        // initilizace namespaces
        this.globalNamespace = new Namespace(this.globalFrameDescriptor);
        //System.out.println("Global context: " + this.globalFrame);
    }

    // Create global frame and fill it with global (built-in) functions.
    private MaterializedFrame initGlobalFrame() {
        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(null,
                this.globalFrameDescriptor);

        addGlobalFunctions(frame);
        return frame.materialize();
    }

    // add all built-in methods
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
