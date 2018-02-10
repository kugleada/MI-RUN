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

public class TSchemeRootNode extends RootNode {

    @Children
    private final TSchemeNode[] bodyNodes;

    @CompilationFinal
    public String name;

    public MaterializedFrame lastFrame = null;

    public TSchemeRootNode(TSchemeNode[] bodyNodes,
                           FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.bodyNodes = bodyNodes;
        System.out.println("RootNode:" + frameDescriptor);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.bodyNodes);
    }

    public void setName(String name) {
        if (this.name == null) {
            this.name = name;
        }
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame virtualFrame) {

        System.out.println("Executing root nodetypes with frame: " + virtualFrame);

        System.out.println("Current frame: " + Truffle.getRuntime().getCurrentFrame());

        int last = this.bodyNodes.length - 1;

        CompilerAsserts.compilationConstant(last);

        System.out.println("Caller frame: " + Truffle.getRuntime().getCallerFrame());

        for (int i=0; i<last; i++) {
            System.out.println(".");
            this.bodyNodes[i].executeGeneric(virtualFrame);
        }

        return this.bodyNodes[last].executeGeneric(virtualFrame);
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
