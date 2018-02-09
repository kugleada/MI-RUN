package cz.schemeinterpreter.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.RootNode;
import cz.schemeinterpreter.nodes.special.DefineNode;

public class SchemeRootNode extends RootNode {
    @Children private final SchemeNode[] bodyNodes;

    public SchemeRootNode(SchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.bodyNodes = bodyNodes;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame virtualFrame) {
        int lastNodeId = this.bodyNodes.length - 1;
        CompilerAsserts.compilationConstant(lastNodeId);
        for (int i=0; i<lastNodeId; i++) {
            this.bodyNodes[i].execute(virtualFrame);
        }
        return this.bodyNodes[lastNodeId].execute(virtualFrame);
    }

    public static SchemeRootNode create(FrameSlot[] argumentNames, SchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {
        SchemeNode[] allNodes = new SchemeNode[argumentNames.length + bodyNodes.length];
        for (int i=0; i<argumentNames.length; i++) {
            allNodes[i] = new DefineNode(new ReadArgumentNode(i), argumentNames[i]);
        }
        System.arraycopy(bodyNodes, 0, allNodes,
                argumentNames.length, bodyNodes.length);
        return new SchemeRootNode(allNodes, frameDescriptor);
    }
}