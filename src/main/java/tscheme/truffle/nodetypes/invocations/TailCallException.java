package tscheme.truffle.nodetypes.invocations;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.ControlFlowException;

public class TailCallException extends ControlFlowException {
    public final CallTarget callTarget;
    public final Object[] arguments;

    public TailCallException(CallTarget callTarget, Object[] arguments) {
        this.callTarget = callTarget;
        this.arguments = arguments;
    }
}
