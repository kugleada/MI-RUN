// CheckStyle: start generated
package tscheme.truffle.nodetypes.controls;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.TSchemeDataTypes;
import tscheme.truffle.TSchemeDataTypesGen;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(QuoteNode.class)
public final class QuoteNodeGen extends QuoteNode {

    private final QuoteKind kind;
    @Child private TSchemeNode literal_;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private QuoteNodeGen(TSchemeNode literal, QuoteKind kind) {
        this.kind = kind;
        this.literal_ = literal;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b11101) == 0 /* only-active quoteLong(VirtualFrame, long) */) {
            return executeGeneric_long0(frameValue, state);
        } else if ((state & 0b11011) == 0 /* only-active quoteBoolean(VirtualFrame, boolean) */) {
            return executeGeneric_boolean1(frameValue, state);
        } else if ((state & 0b10111) == 0 /* only-active quoteDouble(VirtualFrame, double) */) {
            return executeGeneric_double2(frameValue, state);
        } else {
            return executeGeneric_generic3(frameValue, state);
        }
    }

    private Object executeGeneric_long0(VirtualFrame frameValue, int state) {
        long literalValue_;
        try {
            literalValue_ = literal_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */;
        return quoteLong(frameValue, literalValue_);
    }

    private Object executeGeneric_boolean1(VirtualFrame frameValue, int state) {
        boolean literalValue_;
        try {
            literalValue_ = literal_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */;
        return quoteBoolean(frameValue, literalValue_);
    }

    private Object executeGeneric_double2(VirtualFrame frameValue, int state) {
        long literalValue_long = 0L;
        double literalValue_;
        try {
            if ((state & 0b11000001) == 0 /* only-active 0:double */) {
                literalValue_long = literal_.executeLong(frameValue);
                literalValue_ = TSchemeDataTypes.castLongToDouble(literalValue_long);
            } else if ((state & 0b10100001) == 0 /* only-active 0:double */) {
                literalValue_ = literal_.executeDouble(frameValue);
            } else {
                Object literalValue__ = literal_.executeGeneric(frameValue);
                literalValue_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, literalValue__);
            }
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b1000) != 0 /* is-active quoteDouble(VirtualFrame, double) */;
        return quoteDouble(frameValue, literalValue_);
    }

    private Object executeGeneric_generic3(VirtualFrame frameValue, int state) {
        Object literalValue_ = literal_.executeGeneric(frameValue);
        if ((state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */ && literalValue_ instanceof Long) {
            long literalValue__ = (long) literalValue_;
            return quoteLong(frameValue, literalValue__);
        }
        if ((state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */ && literalValue_ instanceof Boolean) {
            boolean literalValue__ = (boolean) literalValue_;
            return quoteBoolean(frameValue, literalValue__);
        }
        if ((state & 0b1000) != 0 /* is-active quoteDouble(VirtualFrame, double) */ && TSchemeDataTypesGen.isImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, literalValue_)) {
            double literalValue__ = TSchemeDataTypesGen.asImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, literalValue_);
            return quoteDouble(frameValue, literalValue__);
        }
        if ((state & 0b10000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return quote(frameValue, literalValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, literalValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectBoolean(executeGeneric(frameValue));
        }
        boolean literalValue_;
        try {
            literalValue_ = literal_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */) {
            return quoteBoolean(frameValue, literalValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue, literalValue_));
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectDouble(executeGeneric(frameValue));
        }
        long literalValue_long = 0L;
        double literalValue_;
        try {
            if ((state & 0b11000001) == 0 /* only-active 0:double */) {
                literalValue_long = literal_.executeLong(frameValue);
                literalValue_ = TSchemeDataTypes.castLongToDouble(literalValue_long);
            } else if ((state & 0b10100001) == 0 /* only-active 0:double */) {
                literalValue_ = literal_.executeDouble(frameValue);
            } else {
                Object literalValue__ = literal_.executeGeneric(frameValue);
                literalValue_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000) >>> 5 /* extract-implicit-active 0:double */, literalValue__);
            }
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b1000) != 0 /* is-active quoteDouble(VirtualFrame, double) */) {
            return quoteDouble(frameValue, literalValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(frameValue, ((state & 0b11000001) == 0 /* only-active 0:double */ ? (Object) literalValue_long : (Object) literalValue_)));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b10000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return TSchemeDataTypesGen.expectLong(executeGeneric(frameValue));
        }
        long literalValue_;
        try {
            literalValue_ = literal_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */) {
            return quoteLong(frameValue, literalValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue, literalValue_));
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object literalValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded quoteLong(VirtualFrame, long) */ && literalValue instanceof Long) {
                long literalValue_ = (long) literalValue;
                this.state_ = state | 0b10 /* add-active quoteLong(VirtualFrame, long) */;
                lock.unlock();
                hasLock = false;
                return quoteLong(frameValue, literalValue_);
            }
            if ((exclude & 0b10) == 0 /* is-not-excluded quoteBoolean(VirtualFrame, boolean) */ && literalValue instanceof Boolean) {
                boolean literalValue_ = (boolean) literalValue;
                this.state_ = state | 0b100 /* add-active quoteBoolean(VirtualFrame, boolean) */;
                lock.unlock();
                hasLock = false;
                return quoteBoolean(frameValue, literalValue_);
            }
            if ((exclude & 0b100) == 0 /* is-not-excluded quoteDouble(VirtualFrame, double) */) {
                int doubleCast0;
                if ((doubleCast0 = TSchemeDataTypesGen.specializeImplicitDouble(literalValue)) != 0) {
                    double literalValue_ = TSchemeDataTypesGen.asImplicitDouble(doubleCast0, literalValue);
                    state = (state | (doubleCast0 << 5) /* set-implicit-active 0:double */);
                    this.state_ = state | 0b1000 /* add-active quoteDouble(VirtualFrame, double) */;
                    lock.unlock();
                    hasLock = false;
                    return quoteDouble(frameValue, literalValue_);
                }
            }
            this.exclude_ = exclude | 0b111 /* add-excluded quoteLong(VirtualFrame, long), quoteBoolean(VirtualFrame, boolean), quoteDouble(VirtualFrame, double) */;
            state = state & 0xfffffff1 /* remove-active quoteLong(VirtualFrame, long), quoteBoolean(VirtualFrame, boolean), quoteDouble(VirtualFrame, double) */;
            this.state_ = state | 0b10000 /* add-active quote(VirtualFrame, Object) */;
            lock.unlock();
            hasLock = false;
            return quote(frameValue, literalValue);
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

    public static QuoteNode create(TSchemeNode literal, QuoteKind kind) {
        return new QuoteNodeGen(literal, kind);
    }

}
