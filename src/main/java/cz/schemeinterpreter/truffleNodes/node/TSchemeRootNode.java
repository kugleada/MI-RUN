package cz.schemeinterpreter.truffleNodes.node;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;

public class TSchemeRootNode extends RootNode {

    @Children
    private final TSchemeNode[] bodyNodes; //< all nodes of program

    /**
     * Create root of program.
     * @param bodyNodes Which program nodes are part of this "subtree".
     * @param frameDescriptor To access environment.
     */
    public TSchemeRootNode(TSchemeNode[] bodyNodes,
                           FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.bodyNodes = bodyNodes;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame virtualFrame) {
        int allButLast = this.bodyNodes.length - 1; // last nodetypes result will be result of all

        CompilerAsserts.compilationConstant(allButLast); // nothing more than confirmation

        // executeGeneric all body nodes except for last
        for (int i=0; i<allButLast; i++) {
            this.bodyNodes[i].execute(virtualFrame);
        }

        // executeGeneric last one and return its return value
        return this.bodyNodes[allButLast].execute(virtualFrame);
    }

    public static RootNode create(FrameSlot[] arguments, TSchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {
        return null;
    }
}
