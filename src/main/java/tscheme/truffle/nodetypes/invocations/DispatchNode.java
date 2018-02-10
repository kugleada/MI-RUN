package tscheme.truffle.nodetypes.invocations;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class DispatchNode extends Node {
    protected static final int INLINE_CACHE_SIZE = 5;

    protected abstract Object executeDispatch(VirtualFrame virtualFrame,
            CallTarget callTarget, Object[] argumentValues);
}
