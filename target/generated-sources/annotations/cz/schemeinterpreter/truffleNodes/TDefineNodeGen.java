// CheckStyle: start generated
package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TDefineNode.class)
public final class TDefineNodeGen extends TDefineNode {

    private final FrameSlot slot;
    @Child private TSchemeNode variableValueNode_;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private TDefineNodeGen(TSchemeNode variableValueNode, FrameSlot slot) {
        this.slot = slot;
        this.variableValueNode_ = variableValueNode;
    }

    @Override
    protected FrameSlot getSlot() {
        return this.slot;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b1101) == 0 /* only-active writeLong(VirtualFrame, long) */) {
            return execute_long0(frameValue, state);
        } else if ((state & 0b1011) == 0 /* only-active writeBoolean(VirtualFrame, boolean) */) {
            return execute_boolean1(frameValue, state);
        } else {
            return execute_generic2(frameValue, state);
        }
    }

    private Object execute_long0(VirtualFrame frameValue, int state) {
        long variableValueNodeValue_;
        try {
            variableValueNodeValue_ = variableValueNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */;
        assert (isLongKind());
        return writeLong(frameValue, variableValueNodeValue_);
    }

    private Object execute_boolean1(VirtualFrame frameValue, int state) {
        boolean variableValueNodeValue_;
        try {
            variableValueNodeValue_ = variableValueNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */;
        assert (isBooleanKind());
        return writeBoolean(frameValue, variableValueNodeValue_);
    }

    private Object execute_generic2(VirtualFrame frameValue, int state) {
        Object variableValueNodeValue_ = variableValueNode_.execute(frameValue);
        if ((state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */ && variableValueNodeValue_ instanceof Long) {
            long variableValueNodeValue__ = (long) variableValueNodeValue_;
            assert (isLongKind());
            return writeLong(frameValue, variableValueNodeValue__);
        }
        if ((state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */ && variableValueNodeValue_ instanceof Boolean) {
            boolean variableValueNodeValue__ = (boolean) variableValueNodeValue_;
            assert (isBooleanKind());
            return writeBoolean(frameValue, variableValueNodeValue__);
        }
        if ((state & 0b1000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return write(frameValue, variableValueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, variableValueNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b1000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return expectBoolean(execute(frameValue));
        }
        boolean variableValueNodeValue_;
        try {
            variableValueNodeValue_ = variableValueNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return expectBoolean(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */) {
            assert (isBooleanKind());
            return writeBoolean(frameValue, variableValueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectBoolean(executeAndSpecialize(frameValue, variableValueNodeValue_));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b1000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return expectLong(execute(frameValue));
        }
        long variableValueNodeValue_;
        try {
            variableValueNodeValue_ = variableValueNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return expectLong(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */) {
            assert (isLongKind());
            return writeLong(frameValue, variableValueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectLong(executeAndSpecialize(frameValue, variableValueNodeValue_));
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object variableValueNodeValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded writeLong(VirtualFrame, long) */ && variableValueNodeValue instanceof Long) {
                long variableValueNodeValue_ = (long) variableValueNodeValue;
                if ((isLongKind())) {
                    this.state_ = state | 0b10 /* add-active writeLong(VirtualFrame, long) */;
                    lock.unlock();
                    hasLock = false;
                    return writeLong(frameValue, variableValueNodeValue_);
                }
            }
            if ((exclude & 0b10) == 0 /* is-not-excluded writeBoolean(VirtualFrame, boolean) */ && variableValueNodeValue instanceof Boolean) {
                boolean variableValueNodeValue_ = (boolean) variableValueNodeValue;
                if ((isBooleanKind())) {
                    this.state_ = state | 0b100 /* add-active writeBoolean(VirtualFrame, boolean) */;
                    lock.unlock();
                    hasLock = false;
                    return writeBoolean(frameValue, variableValueNodeValue_);
                }
            }
            this.exclude_ = exclude | 0b11 /* add-excluded writeLong(VirtualFrame, long), writeBoolean(VirtualFrame, boolean) */;
            state = state & 0xfffffff9 /* remove-active writeLong(VirtualFrame, long), writeBoolean(VirtualFrame, boolean) */;
            this.state_ = state | 0b1000 /* add-active write(VirtualFrame, Object) */;
            lock.unlock();
            hasLock = false;
            return write(frameValue, variableValueNodeValue);
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
        } else if (((state & 0b1110) & ((state & 0b1110) - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    private static boolean expectBoolean(Object value) throws UnexpectedResultException {
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new UnexpectedResultException(value);
    }

    private static long expectLong(Object value) throws UnexpectedResultException {
        if (value instanceof Long) {
            return (long) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static TDefineNode create(TSchemeNode variableValueNode, FrameSlot slot) {
        return new TDefineNodeGen(variableValueNode, slot);
    }

}
