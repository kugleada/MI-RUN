package cz.schemeinterpreter.nodes.builtin;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.ReadArgumentNode;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.nodes.SchemeRootNode;
import cz.schemeinterpreter.types.SchemeFunction;

@NodeChild(value = "arguments", type = SchemeNode[].class)
public abstract class BuiltinNode extends SchemeNode {
    public static SchemeFunction createBuiltinFunction(
            NodeFactory<? extends BuiltinNode> factory /*, VirtualFrame outerFrame*/) { //FrameDesciptor?
        int argumentCount = factory.getExecutionSignature().size();
        SchemeNode[] argumentNodes = new SchemeNode[argumentCount];
        for (int i=0; i<argumentCount; i++) {
            argumentNodes[i] = new ReadArgumentNode(i);
        }
        BuiltinNode node = factory.createNode((Object) argumentNodes);
        return new SchemeFunction(Truffle.getRuntime().createCallTarget(
                new SchemeRootNode(new SchemeNode[] {node},
                        new FrameDescriptor())));
    }
}
