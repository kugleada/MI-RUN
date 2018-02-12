// CheckStyle: start generated
package tscheme.truffle.nodetypes.builtins.basicop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.datatypes.TSchemeDataTypesGen;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(SubBuiltInNode.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public final class SubBuiltInNodeFactory implements NodeFactory<SubBuiltInNode> {

    private static SubBuiltInNodeFactory instance;

    private SubBuiltInNodeFactory() {
    }

    @Override
    public Class<SubBuiltInNode> getNodeClass() {
        return SubBuiltInNode.class;
    }

    @Override
    public List getExecutionSignature() {
        return Arrays.asList(TSchemeNode.class, TSchemeNode.class);
    }

    @Override
    public List getNodeSignatures() {
        return Arrays.asList(Arrays.asList(TSchemeNode[].class));
    }

    @Override
    public SubBuiltInNode createNode(Object... arguments) {
        if (arguments.length == 1 && (arguments[0] == null || arguments[0] instanceof TSchemeNode[])) {
            return create((TSchemeNode[]) arguments[0]);
        } else {
            throw new IllegalArgumentException("Invalid create signature.");
        }
    }

    public static NodeFactory<SubBuiltInNode> getInstance() {
        if (instance == null) {
            instance = new SubBuiltInNodeFactory();
        }
        return instance;
    }

    public static SubBuiltInNode create(TSchemeNode[] arguments) {
        return new SubBuiltInNodeGen(arguments);
    }

    @GeneratedBy(SubBuiltInNode.class)
    public static final class SubBuiltInNodeGen extends SubBuiltInNode {

        @Child private TSchemeNode arguments0_;
        @Child private TSchemeNode arguments1_;
        @CompilationFinal private int state_ = 1;
        @CompilationFinal private int exclude_;

        private SubBuiltInNodeGen(TSchemeNode[] arguments) {
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            if ((state & 0b111101) == 0 /* only-active minus(long, long) */) {
                return executeGeneric_long_long0(frameValue, state);
            } else if ((state & 0b110111) == 0 /* only-active minus(double, double) */) {
                return executeGeneric_double_double1(frameValue, state);
            } else if ((state & 0b101111) == 0 /* only-active minus(double, long) */) {
                return executeGeneric_double_long2(frameValue, state);
            } else if ((state & 0b11111) == 0 /* only-active minus(double, BigInteger) */) {
                return executeGeneric_double3(frameValue, state);
            } else {
                return executeGeneric_generic4(frameValue, state);
            }
        }

        private Object executeGeneric_long_long0(VirtualFrame frameValue, int state) {
            long arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            long arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert (state & 0b10) != 0 /* is-active minus(long, long) */;
            try {
                return minus(arguments0Value_, arguments1Value_);
            } catch (ArithmeticException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1 /* add-excluded minus(long, long) */;
                    this.state_ = this.state_ & 0xfffffffd /* remove-active minus(long, long) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
        }

        private Object executeGeneric_double_double1(VirtualFrame frameValue, int state) {
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            double arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert (state & 0b1000) != 0 /* is-active minus(double, double) */;
            return minus(arguments0Value_, arguments1Value_);
        }

        private Object executeGeneric_double_long2(VirtualFrame frameValue, int state) {
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            long arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(arguments0Value_, ex.getResult());
            }
            assert (state & 0b10000) != 0 /* is-active minus(double, long) */;
            return minus(arguments0Value_, arguments1Value_);
        }

        private Object executeGeneric_double3(VirtualFrame frameValue, int state) {
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            Object arguments1Value_ = arguments1_.executeGeneric(frameValue);
            assert (state & 0b100000) != 0 /* is-active minus(double, BigInteger) */;
            if (TSchemeDataTypesGen.isImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                return minus(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        private Object executeGeneric_generic4(VirtualFrame frameValue, int state) {
            Object arguments0Value_ = arguments0_.executeGeneric(frameValue);
            Object arguments1Value_ = arguments1_.executeGeneric(frameValue);
            if ((state & 0b10) != 0 /* is-active minus(long, long) */ && arguments0Value_ instanceof Long) {
                long arguments0Value__ = (long) arguments0Value_;
                if (arguments1Value_ instanceof Long) {
                    long arguments1Value__ = (long) arguments1Value_;
                    try {
                        return minus(arguments0Value__, arguments1Value__);
                    } catch (ArithmeticException ex) {
                        // implicit transferToInterpreterAndInvalidate()
                        Lock lock = getLock();
                        lock.lock();
                        try {
                            this.exclude_ = this.exclude_ | 0b1 /* add-excluded minus(long, long) */;
                            this.state_ = this.state_ & 0xfffffffd /* remove-active minus(long, long) */;
                        } finally {
                            lock.unlock();
                        }
                        return executeAndSpecialize(arguments0Value__, arguments1Value__);
                    }
                }
            }
            if ((state & 0b100) != 0 /* is-active minus(BigInteger, BigInteger) */ && TSchemeDataTypesGen.isImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 0:BigInteger */, arguments0Value_)) {
                BigInteger arguments0Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 0:BigInteger */, arguments0Value_);
                if (TSchemeDataTypesGen.isImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                    BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                    return minus(arguments0Value__, arguments1Value__);
                }
            }
            if ((state & 0b111000) != 0 /* is-active minus(double, double) || minus(double, long) || minus(double, BigInteger) */ && arguments0Value_ instanceof Double) {
                double arguments0Value__ = (double) arguments0Value_;
                if ((state & 0b1000) != 0 /* is-active minus(double, double) */ && arguments1Value_ instanceof Double) {
                    double arguments1Value__ = (double) arguments1Value_;
                    return minus(arguments0Value__, arguments1Value__);
                }
                if ((state & 0b10000) != 0 /* is-active minus(double, long) */ && arguments1Value_ instanceof Long) {
                    long arguments1Value__ = (long) arguments1Value_;
                    return minus(arguments0Value__, arguments1Value__);
                }
                if ((state & 0b100000) != 0 /* is-active minus(double, BigInteger) */ && TSchemeDataTypesGen.isImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                    BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                    return minus(arguments0Value__, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state = state_;
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(ex.getResult(), arguments1Value));
            }
            if ((state & 0b110111) == 0 /* only-active minus(double, double) */) {
                return executeDouble_double5(frameValue, state, arguments0Value_);
            } else if ((state & 0b101111) == 0 /* only-active minus(double, long) */) {
                return executeDouble_long6(frameValue, state, arguments0Value_);
            } else {
                return executeDouble_generic7(frameValue, state, arguments0Value_);
            }
        }

        private double executeDouble_double5(VirtualFrame frameValue, int state, double arguments0Value_) throws UnexpectedResultException {
            double arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(arguments0Value_, ex.getResult()));
            }
            assert (state & 0b1000) != 0 /* is-active minus(double, double) */;
            return minus(arguments0Value_, arguments1Value_);
        }

        private double executeDouble_long6(VirtualFrame frameValue, int state, double arguments0Value_) throws UnexpectedResultException {
            long arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(arguments0Value_, ex.getResult()));
            }
            assert (state & 0b10000) != 0 /* is-active minus(double, long) */;
            return minus(arguments0Value_, arguments1Value_);
        }

        private double executeDouble_generic7(VirtualFrame frameValue, int state, double arguments0Value_) throws UnexpectedResultException {
            Object arguments1Value_ = arguments1_.executeGeneric(frameValue);
            if ((state & 0b1000) != 0 /* is-active minus(double, double) */ && arguments1Value_ instanceof Double) {
                double arguments1Value__ = (double) arguments1Value_;
                return minus(arguments0Value_, arguments1Value__);
            }
            if ((state & 0b10000) != 0 /* is-active minus(double, long) */ && arguments1Value_ instanceof Long) {
                long arguments1Value__ = (long) arguments1Value_;
                return minus(arguments0Value_, arguments1Value__);
            }
            if ((state & 0b100000) != 0 /* is-active minus(double, BigInteger) */ && TSchemeDataTypesGen.isImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b1100000000) >>> 8 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                return minus(arguments0Value_, arguments1Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
            int state = state_;
            long arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return TSchemeDataTypesGen.expectLong(executeAndSpecialize(ex.getResult(), arguments1Value));
            }
            long arguments1Value_;
            try {
                arguments1Value_ = arguments1_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectLong(executeAndSpecialize(arguments0Value_, ex.getResult()));
            }
            if ((state & 0b10) != 0 /* is-active minus(long, long) */) {
                try {
                    return minus(arguments0Value_, arguments1Value_);
                } catch (ArithmeticException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    Lock lock = getLock();
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b1 /* add-excluded minus(long, long) */;
                        this.state_ = this.state_ & 0xfffffffd /* remove-active minus(long, long) */;
                    } finally {
                        lock.unlock();
                    }
                    return TSchemeDataTypesGen.expectLong(executeAndSpecialize(arguments0Value_, arguments1Value_));
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TSchemeDataTypesGen.expectLong(executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
                int exclude = exclude_;
                if ((exclude & 0b1) == 0 /* is-not-excluded minus(long, long) */ && arguments0Value instanceof Long) {
                    long arguments0Value_ = (long) arguments0Value;
                    if (arguments1Value instanceof Long) {
                        long arguments1Value_ = (long) arguments1Value;
                        this.state_ = state | 0b10 /* add-active minus(long, long) */;
                        try {
                            lock.unlock();
                            hasLock = false;
                            return minus(arguments0Value_, arguments1Value_);
                        } catch (ArithmeticException ex) {
                            // implicit transferToInterpreterAndInvalidate()
                            lock.lock();
                            try {
                                this.exclude_ = this.exclude_ | 0b1 /* add-excluded minus(long, long) */;
                                this.state_ = this.state_ & 0xfffffffd /* remove-active minus(long, long) */;
                            } finally {
                                lock.unlock();
                            }
                            return executeAndSpecialize(arguments0Value_, arguments1Value_);
                        }
                    }
                }
                {
                    int bigIntegerCast0;
                    if ((bigIntegerCast0 = TSchemeDataTypesGen.specializeImplicitBigInteger(arguments0Value)) != 0) {
                        BigInteger arguments0Value_ = TSchemeDataTypesGen.asImplicitBigInteger(bigIntegerCast0, arguments0Value);
                        int bigIntegerCast1;
                        if ((bigIntegerCast1 = TSchemeDataTypesGen.specializeImplicitBigInteger(arguments1Value)) != 0) {
                            BigInteger arguments1Value_ = TSchemeDataTypesGen.asImplicitBigInteger(bigIntegerCast1, arguments1Value);
                            state = (state | (bigIntegerCast0 << 6) /* set-implicit-active 0:BigInteger */);
                            state = (state | (bigIntegerCast1 << 8) /* set-implicit-active 1:BigInteger */);
                            this.state_ = state | 0b100 /* add-active minus(BigInteger, BigInteger) */;
                            lock.unlock();
                            hasLock = false;
                            return minus(arguments0Value_, arguments1Value_);
                        }
                    }
                }
                if (arguments0Value instanceof Double) {
                    double arguments0Value_ = (double) arguments0Value;
                    if (arguments1Value instanceof Double) {
                        double arguments1Value_ = (double) arguments1Value;
                        this.state_ = state | 0b1000 /* add-active minus(double, double) */;
                        lock.unlock();
                        hasLock = false;
                        return minus(arguments0Value_, arguments1Value_);
                    }
                    if (arguments1Value instanceof Long) {
                        long arguments1Value_ = (long) arguments1Value;
                        this.state_ = state | 0b10000 /* add-active minus(double, long) */;
                        lock.unlock();
                        hasLock = false;
                        return minus(arguments0Value_, arguments1Value_);
                    }
                    {
                        int bigIntegerCast1;
                        if ((bigIntegerCast1 = TSchemeDataTypesGen.specializeImplicitBigInteger(arguments1Value)) != 0) {
                            BigInteger arguments1Value_ = TSchemeDataTypesGen.asImplicitBigInteger(bigIntegerCast1, arguments1Value);
                            state = (state | (bigIntegerCast1 << 8) /* set-implicit-active 1:BigInteger */);
                            this.state_ = state | 0b100000 /* add-active minus(double, BigInteger) */;
                            lock.unlock();
                            hasLock = false;
                            return minus(arguments0Value_, arguments1Value_);
                        }
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new UnsupportedSpecializationException(this, new Node[] {this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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

    }
}
