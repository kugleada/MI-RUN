package tscheme.truffle.nodetypes.invocations;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;

public class DirectDispatchNode extends DispatchNode {

    private final CallTarget cachedCallTarget;

    @Child private DirectCallNode callCachedTargetNode;
    @Child private DispatchNode nextNode;

    public DirectDispatchNode(DispatchNode next, CallTarget callTarget) {
        this.cachedCallTarget = callTarget;
        this.callCachedTargetNode = Truffle.getRuntime().createDirectCallNode(
                this.cachedCallTarget);
        this.nextNode = next;
    }

    @Override
    protected Object executeDispatch(VirtualFrame frame, CallTarget callTarget,
            Object[] arguments) {
        //System.out.println("Executing dispatch:");
        if (this.cachedCallTarget == callTarget) {
            return this.callCachedTargetNode.call(arguments);
        }
        return this.nextNode.executeDispatch(frame, callTarget, arguments);
    }
}