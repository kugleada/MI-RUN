// CheckStyle: start generated
package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TSymbolNode.class)
public final class TSymbolNodeGen extends TSymbolNode {

    private final FrameSlot slot;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private TSymbolNodeGen(FrameSlot slot) {
        this.slot = slot;
    }

    @Override
    public FrameSlot getSlot() {
        return this.slot;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b10) != 0 /* is-active readLong(VirtualFrame) */) {
            try {
                return readLong(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1 /* add-excluded readLong(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffffd /* remove-active readLong(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue);
            }
        }
        if ((state & 0b100) != 0 /* is-active readBoolean(VirtualFrame) */) {
            try {
                return readBoolean(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b10 /* add-excluded readBoolean(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffffb /* remove-active readBoolean(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue);
            }
        }
        if ((state & 0b1000) != 0 /* is-active readObject(VirtualFrame) */) {
            try {
                return readObject(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b100 /* add-excluded readObject(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffff7 /* remove-active readObject(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue);
            }
        }
        if ((state & 0b10000) != 0 /* is-active read(VirtualFrame) */) {
            return read(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b11000) != 0 /* is-active readObject(VirtualFrame) || read(VirtualFrame) */) {
            return expectBoolean(execute(frameValue));
        }
        if ((state & 0b100) != 0 /* is-active readBoolean(VirtualFrame) */) {
            try {
                return readBoolean(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b10 /* add-excluded readBoolean(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffffb /* remove-active readBoolean(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return expectBoolean(executeAndSpecialize(frameValue));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectBoolean(executeAndSpecialize(frameValue));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b11000) != 0 /* is-active readObject(VirtualFrame) || read(VirtualFrame) */) {
            return expectLong(execute(frameValue));
        }
        if ((state & 0b10) != 0 /* is-active readLong(VirtualFrame) */) {
            try {
                return readLong(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1 /* add-excluded readLong(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffffd /* remove-active readLong(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return expectLong(executeAndSpecialize(frameValue));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectLong(executeAndSpecialize(frameValue));
    }

    private Object executeAndSpecialize(VirtualFrame frameValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
            int exclude = exclude_;
            if ((exclude & 0b1) == 0 /* is-not-excluded readLong(VirtualFrame) */) {
                this.state_ = state | 0b10 /* add-active readLong(VirtualFrame) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return readLong(frameValue);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b1 /* add-excluded readLong(VirtualFrame) */;
                        this.state_ = this.state_ & 0xfffffffd /* remove-active readLong(VirtualFrame) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue);
                }
            }
            if ((exclude & 0b10) == 0 /* is-not-excluded readBoolean(VirtualFrame) */) {
                this.state_ = state | 0b100 /* add-active readBoolean(VirtualFrame) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return readBoolean(frameValue);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b10 /* add-excluded readBoolean(VirtualFrame) */;
                        this.state_ = this.state_ & 0xfffffffb /* remove-active readBoolean(VirtualFrame) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue);
                }
            }
            if ((exclude & 0b100) == 0 /* is-not-excluded readObject(VirtualFrame) */) {
                this.state_ = state | 0b1000 /* add-active readObject(VirtualFrame) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return readObject(frameValue);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b100 /* add-excluded readObject(VirtualFrame) */;
                        this.state_ = this.state_ & 0xfffffff7 /* remove-active readObject(VirtualFrame) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue);
                }
            }
            this.exclude_ = exclude | 0b111 /* add-excluded readLong(VirtualFrame), readBoolean(VirtualFrame), readObject(VirtualFrame) */;
            state = state & 0xfffffff1 /* remove-active readLong(VirtualFrame), readBoolean(VirtualFrame), readObject(VirtualFrame) */;
            this.state_ = state | 0b10000 /* add-active read(VirtualFrame) */;
            lock.unlock();
            hasLock = false;
            return read(frameValue);
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

    public static TSymbolNode create(FrameSlot slot) {
        return new TSymbolNodeGen(slot);
    }

}
