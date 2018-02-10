// CheckStyle: start generated
package tscheme.truffle.nodetypes.controls;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.TSchemeDataTypes;
import tscheme.truffle.TSchemeDataTypesGen;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(DefineNode.class)
public final class DefineNodeGen extends DefineNode {

    private final FrameSlot slot;
    @Child private TSchemeNode valueNode_;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private DefineNodeGen(TSchemeNode valueNode, FrameSlot slot) {
        this.slot = slot;
        this.valueNode_ = valueNode;
    }

    @Override
    protected Node getValueNode() {
        return this.valueNode_;
    }

    @Override
    protected FrameSlot getSlot() {
        return this.slot;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b11101) == 0 /* only-active writeLong(VirtualFrame, long) */) {
            return executeGeneric_long0(frameValue, state);
        } else if ((state & 0b11011) == 0 /* only-active writeBoolean(VirtualFrame, boolean) */) {
            return executeGeneric_boolean1(frameValue, state);
        } else if ((state & 0b10111) == 0 /* only-active writeDouble(VirtualFrame, double) */) {
            return executeGeneric_double2(frameValue, state);
        } else {
            return executeGeneric_generic3(frameValue, state);
        }
    }

    private Object executeGeneric_long0(VirtualFrame frameValue, int state) {
        long valueNodeValue_;
        try {
            valueNodeValue_ = valueNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */;
        return writeLong(frameValue, valueNodeValue_);
    }

    private Object executeGeneric_boolean1(VirtualFrame frameValue, int state) {
        boolean valueNodeValue_;
        try {
            valueNodeValue_ = valueNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */;
        return writeBoolean(frameValue, valueNodeValue_);
    }

    private Object executeGeneric_double2(VirtualFrame frameValue, int state) {
        long valueNodeValue_long = 0L;
        double valueNodeValue_;
        try {
            if ((state & 0b11000001) == 0 /* only-active 0:double */) {
                valueNodeValue_long = valueNode_.executeLong(frameValue);
                valueNodeValue_ = TSchemeDataTypes.castLongToDouble(valueNodeValue_long);
            } else if ((state & 0b10100001) == 0 /* only-active 0:double */) {
                valueNodeValue_ = valueNode_.executeDouble(frameValue);
            } else {
                Object valueNodeValue__ = valueNode_.executeGeneric(frameValue);
                valueNodeValue_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, valueNodeValue__);
            }
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b1000) != 0 /* is-active writeDouble(VirtualFrame, double) */;
        return writeDouble(frameValue, valueNodeValue_);
    }

    private Object executeGeneric_generic3(VirtualFrame frameValue, int state) {
        Object valueNodeValue_ = valueNode_.executeGeneric(frameValue);
        if ((state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */ && valueNodeValue_ instanceof Long) {
            long valueNodeValue__ = (long) valueNodeValue_;
            return writeLong(frameValue, valueNodeValue__);
        }
        if ((state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */ && valueNodeValue_ instanceof Boolean) {
            boolean valueNodeValue__ = (boolean) valueNodeValue_;
            return writeBoolean(frameValue, valueNodeValue__);
        }
        if ((state & 0b1000) != 0 /* is-active writeDouble(VirtualFrame, double) */ && TSchemeDataTypesGen.isImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, valueNodeValue_)) {
            double valueNodeValue__ = TSchemeDataTypesGen.asImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, valueNodeValue_);
            return writeDouble(frameValue, valueNodeValue__);
        }
        if ((state & 0b10000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return write(frameValue, valueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, valueNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectBoolean(executeGeneric(frameValue));
        }
        boolean valueNodeValue_;
        try {
            valueNodeValue_ = valueNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b100) != 0 /* is-active writeBoolean(VirtualFrame, boolean) */) {
            return writeBoolean(frameValue, valueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue, valueNodeValue_));
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectDouble(executeGeneric(frameValue));
        }
        long valueNodeValue_long = 0L;
        double valueNodeValue_;
        try {
            if ((state & 0b11000001) == 0 /* only-active 0:double */) {
                valueNodeValue_long = valueNode_.executeLong(frameValue);
                valueNodeValue_ = TSchemeDataTypes.castLongToDouble(valueNodeValue_long);
            } else if ((state & 0b10100001) == 0 /* only-active 0:double */) {
                valueNodeValue_ = valueNode_.executeDouble(frameValue);
            } else {
                Object valueNodeValue__ = valueNode_.executeGeneric(frameValue);
                valueNodeValue_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, valueNodeValue__);
            }
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b1000) != 0 /* is-active writeDouble(VirtualFrame, double) */) {
            return writeDouble(frameValue, valueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(frameValue, ((state & 0b11000001) == 0 /* only-active 0:double */ ? (Object) valueNodeValue_long : (Object) valueNodeValue_)));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active write(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectLong(executeGeneric(frameValue));
        }
        long valueNodeValue_;
        try {
            valueNodeValue_ = valueNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b10) != 0 /* is-active writeLong(VirtualFrame, long) */) {
            return writeLong(frameValue, valueNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue, valueNodeValue_));
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object valueNodeValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded writeLong(VirtualFrame, long) */ && valueNodeValue instanceof Long) {
                long valueNodeValue_ = (long) valueNodeValue;
                this.state_ = state | 0b10 /* add-active writeLong(VirtualFrame, long) */;
                lock.unlock();
                hasLock = false;
                return writeLong(frameValue, valueNodeValue_);
            }
            if ((exclude & 0b10) == 0 /* is-not-excluded writeBoolean(VirtualFrame, boolean) */ && valueNodeValue instanceof Boolean) {
                boolean valueNodeValue_ = (boolean) valueNodeValue;
                this.state_ = state | 0b100 /* add-active writeBoolean(VirtualFrame, boolean) */;
                lock.unlock();
                hasLock = false;
                return writeBoolean(frameValue, valueNodeValue_);
            }
            if ((exclude & 0b100) == 0 /* is-not-excluded writeDouble(VirtualFrame, double) */) {
                int doubleCast0;
                if ((doubleCast0 = TSchemeDataTypesGen.specializeImplicitDouble(valueNodeValue)) != 0) {
                    double valueNodeValue_ = TSchemeDataTypesGen.asImplicitDouble(doubleCast0, valueNodeValue);
                    state = (state | (doubleCast0 << 5) /* set-implicit-active 0:double */);
                    this.state_ = state | 0b1000 /* add-active writeDouble(VirtualFrame, double) */;
                    lock.unlock();
                    hasLock = false;
                    return writeDouble(frameValue, valueNodeValue_);
                }
            }
            this.exclude_ = exclude | 0b111 /* add-excluded writeLong(VirtualFrame, long), writeBoolean(VirtualFrame, boolean), writeDouble(VirtualFrame, double) */;
            state = state & 0xfffffff1 /* remove-active writeLong(VirtualFrame, long), writeBoolean(VirtualFrame, boolean), writeDouble(VirtualFrame, double) */;
            this.state_ = state | 0b10000 /* add-active write(VirtualFrame, Object) */;
            lock.unlock();
            hasLock = false;
            return write(frameValue, valueNodeValue);
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
        } else if (((state & 0b11110) & ((state & 0b11110) - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static DefineNode create(TSchemeNode valueNode, FrameSlot slot) {
        return new DefineNodeGen(valueNode, slot);
    }

}
