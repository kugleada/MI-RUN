package tscheme.truffle.nodetypes;

import java.util.Arrays;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.MaterializedFrame;
import tscheme.truffle.nodetypes.read.ReadArgumentNode;
import tscheme.truffle.nodetypes.controls.DefineNodeGen;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;

/**
 * Root node of whole execution unit.
 */
public class TSchemeRootNode extends RootNode {

    @Children
    private final TSchemeNode[] bodyNodes; //< body nodes

    @CompilationFinal
    public String name; // name of this Root node

    /**
     * Constructor for root node.
     * @param bodyNodes Body nodes.
     * @param frameDescriptor Which frame descriptor should be used.
     */
    public TSchemeRootNode(TSchemeNode[] bodyNodes,
                           FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.bodyNodes = bodyNodes;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.bodyNodes);
    }

    // Set name for this root node.
    public void setName(String name) {
        if (this.name == null) {
            this.name = name;
        }
    }

    /**
     * Execution of root node.
     * @param frame Scope.
     * @return Result of execution of last node (child).
     */
    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
/*
        System.out.println("Executing root nodetypes with frame: " + virtualFrame);

        System.out.println("Current frame: " + Truffle.getRuntime().getCurrentFrame());
*/
        // which node is the last
        int lastNode = this.bodyNodes.length - 1;

        //CompilerAsserts.compilationConstant(last);

        //System.out.println("Caller frame: " + Truffle.getRuntime().getCallerFrame());

        // execute body except for last one
        for (int i=0; i < lastNode; ++i) {
            //System.out.println(".");
            this.bodyNodes[i].executeGeneric(frame);
        }

        // execute last one and return its return value
        return this.bodyNodes[lastNode].executeGeneric(frame);
    }

    public static TSchemeRootNode create(FrameSlot[] argumentNames,
                                         TSchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {

        TSchemeNode[] allNodes = new TSchemeNode[argumentNames.length
                + bodyNodes.length];
        for (int i = 0; i < argumentNames.length; i++) {
            allNodes[i] = DefineNodeGen.create(
                    new ReadArgumentNode(i), argumentNames[i]);
        }
        System.arraycopy(bodyNodes, 0, allNodes,
                argumentNames.length, bodyNodes.length);
        return new TSchemeRootNode(allNodes, frameDescriptor);
    }

}
