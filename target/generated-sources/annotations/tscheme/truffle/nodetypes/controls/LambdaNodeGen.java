// CheckStyle: start generated
package tscheme.truffle.nodetypes.controls;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.datatypes.TSchemeFunction;

@GeneratedBy(LambdaNode.class)
public final class LambdaNodeGen extends LambdaNode {

    private final TSchemeFunction function;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private LambdaNodeGen(TSchemeFunction function) {
        this.function = function;
    }

    @Override
    public TSchemeFunction getFunction() {
        return this.function;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b10) != 0 /* is-active getFunctionWithScope(VirtualFrame) */) {
            assert (isScopeSet());
            return getFunctionWithScope(frameValue);
        }
        if ((state & 0b100) != 0 /* is-active getTSchemeFunction(VirtualFrame) */) {
            return getTSchemeFunction(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded getFunctionWithScope(VirtualFrame) */) {
                if ((isScopeSet())) {
                    this.state_ = state | 0b10 /* add-active getFunctionWithScope(VirtualFrame) */;
                    lock.unlock();
                    hasLock = false;
                    return getFunctionWithScope(frameValue);
                }
            }
            this.exclude_ = exclude | 0b1 /* add-excluded getFunctionWithScope(VirtualFrame) */;
            state = state & 0xfffffffd /* remove-active getFunctionWithScope(VirtualFrame) */;
            this.state_ = state | 0b100 /* add-active getTSchemeFunction(VirtualFrame) */;
            lock.unlock();
            hasLock = false;
            return getTSchemeFunction(frameValue);
        } finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
        if (state == 0b0) {
            return NodeCost.UNINITIALIZED;
        } else if (((state & 0b110) & ((state & 0b110) - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static LambdaNode create(TSchemeFunction function) {
        return new LambdaNodeGen(function);
    }

}
