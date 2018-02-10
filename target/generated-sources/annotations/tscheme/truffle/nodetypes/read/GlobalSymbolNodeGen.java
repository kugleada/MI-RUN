// CheckStyle: start generated
package tscheme.truffle.nodetypes.read;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.TSchemeDataTypesGen;

@GeneratedBy(GlobalSymbolNode.class)
public final class GlobalSymbolNodeGen extends GlobalSymbolNode {

    private final FrameSlot slot;
    private final MaterializedFrame globalFrame;
    @CompilationFinal private int state_ = 1;
    @CompilationFinal private int exclude_;

    private GlobalSymbolNodeGen(FrameSlot slot, MaterializedFrame globalFrame) {
        this.slot = slot;
        this.globalFrame = globalFrame;
    }

    @Override
    public FrameSlot getSlot() {
        return this.slot;
    }

    @Override
    public MaterializedFrame getGlobalFrame() {
        return this.globalFrame;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
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
        if ((state & 0b1000) != 0 /* is-active readDouble(VirtualFrame) */) {
            try {
                return readDouble(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b100 /* add-excluded readDouble(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffff7 /* remove-active readDouble(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue);
            }
        }
        if ((state & 0b10000) != 0 /* is-active readObject(VirtualFrame) */) {
            try {
                return readObject(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1000 /* add-excluded readObject(VirtualFrame) */;
                    this.state_ = this.state_ & 0xffffffef /* remove-active readObject(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue);
            }
        }
        if ((state & 0b100000) != 0 /* is-active read(VirtualFrame) */) {
            return read(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b110000) != 0 /* is-active readObject(VirtualFrame) || read(VirtualFrame) */) {
            return TSchemeDataTypesGen.expectBoolean(executeGeneric(frameValue));
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
                return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue));
            }
        }
        if ((state & 0b1000) != 0 /* is-active readDouble(VirtualFrame) */) {
            try {
                return readDouble(frameValue);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b100 /* add-excluded readDouble(VirtualFrame) */;
                    this.state_ = this.state_ & 0xfffffff7 /* remove-active readDouble(VirtualFrame) */;
                } finally {
                    lock.unlock();
                }
                return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(frameValue));
    }

    @Override
    public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0b110000) != 0 /* is-active readObject(VirtualFrame) || read(VirtualFrame) */) {
            return TSchemeDataTypesGen.expectLong(executeGeneric(frameValue));
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
                return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue));
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return TSchemeDataTypesGen.expectLong(executeAndSpecialize(frameValue));
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
            if ((exclude & 0b100) == 0 /* is-not-excluded readDouble(VirtualFrame) */) {
                this.state_ = state | 0b1000 /* add-active readDouble(VirtualFrame) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return readDouble(frameValue);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b100 /* add-excluded readDouble(VirtualFrame) */;
                        this.state_ = this.state_ & 0xfffffff7 /* remove-active readDouble(VirtualFrame) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue);
                }
            }
            if ((exclude & 0b1000) == 0 /* is-not-excluded readObject(VirtualFrame) */) {
                this.state_ = state | 0b10000 /* add-active readObject(VirtualFrame) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return readObject(frameValue);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b1000 /* add-excluded readObject(VirtualFrame) */;
                        this.state_ = this.state_ & 0xffffffef /* remove-active readObject(VirtualFrame) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue);
                }
            }
            this.exclude_ = exclude | 0b1111 /* add-excluded readLong(VirtualFrame), readBoolean(VirtualFrame), readDouble(VirtualFrame), readObject(VirtualFrame) */;
            state = state & 0xffffffe1 /* remove-active readLong(VirtualFrame), readBoolean(VirtualFrame), readDouble(VirtualFrame), readObject(VirtualFrame) */;
            this.state_ = state | 0b100000 /* add-active read(VirtualFrame) */;
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
        } else if (((state & 0b111110) & ((state & 0b111110) - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static GlobalSymbolNode create(FrameSlot slot, MaterializedFrame globalFrame) {
        return new GlobalSymbolNodeGen(slot, globalFrame);
    }

}
