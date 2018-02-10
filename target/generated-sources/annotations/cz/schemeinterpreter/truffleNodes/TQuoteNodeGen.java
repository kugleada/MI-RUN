// CheckStyle: start generated
package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TQuoteNode.class)
public final class TQuoteNodeGen extends TQuoteNode {

    private final QuoteKind kind;
    @Child private TSchemeNode literalNode_;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private TQuoteNodeGen(TSchemeNode literalNode, QuoteKind kind) {
        this.kind = kind;
        this.literalNode_ = literalNode;
    }

    @Override
    protected QuoteKind getKind() {
        return this.kind;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b1101) == 0 /* only-active quoteLong(VirtualFrame, long) */) {
            return execute_long0(frameValue, state);
        } else if ((state & 0b1011) == 0 /* only-active quoteBoolean(VirtualFrame, boolean) */) {
            return execute_boolean1(frameValue, state);
        } else {
            return execute_generic2(frameValue, state);
        }
    }

    private Object execute_long0(VirtualFrame frameValue, int state) {
        long literalNodeValue_;
        try {
            literalNodeValue_ = literalNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */;
        assert (isLongKind());
        return quoteLong(frameValue, literalNodeValue_);
    }

    private Object execute_boolean1(VirtualFrame frameValue, int state) {
        boolean literalNodeValue_;
        try {
            literalNodeValue_ = literalNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */;
        assert (isBooleanKind());
        return quoteBoolean(frameValue, literalNodeValue_);
    }

    private Object execute_generic2(VirtualFrame frameValue, int state) {
        Object literalNodeValue_ = literalNode_.execute(frameValue);
        if ((state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */ && literalNodeValue_ instanceof Long) {
            long literalNodeValue__ = (long) literalNodeValue_;
            assert (isLongKind());
            return quoteLong(frameValue, literalNodeValue__);
        }
        if ((state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */ && literalNodeValue_ instanceof Boolean) {
            boolean literalNodeValue__ = (boolean) literalNodeValue_;
            assert (isBooleanKind());
            return quoteBoolean(frameValue, literalNodeValue__);
        }
        if ((state & 0b1000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return quote(frameValue, literalNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, literalNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b1000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return expectBoolean(execute(frameValue));
        }
        boolean literalNodeValue_;
        try {
            literalNodeValue_ = literalNode_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return expectBoolean(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b100) != 0 /* is-active quoteBoolean(VirtualFrame, boolean) */) {
            assert (isBooleanKind());
            return quoteBoolean(frameValue, literalNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectBoolean(executeAndSpecialize(frameValue, literalNodeValue_));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b1000) != 0 /* is-active quote(VirtualFrame, Object) */) {
            return expectLong(execute(frameValue));
        }
        long literalNodeValue_;
        try {
            literalNodeValue_ = literalNode_.executeLong(frameValue);
        } catch (UnexpectedResultException ex) {
            return expectLong(executeAndSpecialize(frameValue, ex.getResult()));
        }
        if ((state & 0b10) != 0 /* is-active quoteLong(VirtualFrame, long) */) {
            assert (isLongKind());
            return quoteLong(frameValue, literalNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectLong(executeAndSpecialize(frameValue, literalNodeValue_));
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object literalNodeValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded quoteLong(VirtualFrame, long) */ && literalNodeValue instanceof Long) {
                long literalNodeValue_ = (long) literalNodeValue;
                if ((isLongKind())) {
                    this.state_ = state | 0b10 /* add-active quoteLong(VirtualFrame, long) */;
                    lock.unlock();
                    hasLock = false;
                    return quoteLong(frameValue, literalNodeValue_);
                }
            }
            if ((exclude & 0b10) == 0 /* is-not-excluded quoteBoolean(VirtualFrame, boolean) */ && literalNodeValue instanceof Boolean) {
                boolean literalNodeValue_ = (boolean) literalNodeValue;
                if ((isBooleanKind())) {
                    this.state_ = state | 0b100 /* add-active quoteBoolean(VirtualFrame, boolean) */;
                    lock.unlock();
                    hasLock = false;
                    return quoteBoolean(frameValue, literalNodeValue_);
                }
            }
            this.exclude_ = exclude | 0b11 /* add-excluded quoteLong(VirtualFrame, long), quoteBoolean(VirtualFrame, boolean) */;
            state = state & 0xfffffff9 /* remove-active quoteLong(VirtualFrame, long), quoteBoolean(VirtualFrame, boolean) */;
            this.state_ = state | 0b1000 /* add-active quote(VirtualFrame, Object) */;
            lock.unlock();
            hasLock = false;
            return quote(frameValue, literalNodeValue);
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

    public static TQuoteNode create(TSchemeNode literalNode, QuoteKind kind) {
        return new TQuoteNodeGen(literalNode, kind);
    }

}
