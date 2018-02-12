// CheckStyle: start generated
package tscheme.truffle.nodetypes.builtins.relationop;

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

@GeneratedBy(LessThanBuiltInNode.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public final class LessThanBuiltInNodeFactory implements NodeFactory<LessThanBuiltInNode> {

    private static LessThanBuiltInNodeFactory instance;

    private LessThanBuiltInNodeFactory() {
    }

    @Override
    public Class<LessThanBuiltInNode> getNodeClass() {
        return LessThanBuiltInNode.class;
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
    public LessThanBuiltInNode createNode(Object... arguments) {
        if (arguments.length == 1 && (arguments[0] == null || arguments[0] instanceof TSchemeNode[])) {
            return create((TSchemeNode[]) arguments[0]);
        } else {
            throw new IllegalArgumentException("Invalid create signature.");
        }
    }

    public static NodeFactory<LessThanBuiltInNode> getInstance() {
        if (instance == null) {
            instance = new LessThanBuiltInNodeFactory();
        }
        return instance;
    }

    public static LessThanBuiltInNode create(TSchemeNode[] arguments) {
        return new LessThanBuiltInNodeGen(arguments);
    }

    @GeneratedBy(LessThanBuiltInNode.class)
    public static final class LessThanBuiltInNodeGen extends LessThanBuiltInNode {

        @Child private TSchemeNode arguments0_;
        @Child private TSchemeNode arguments1_;
        @CompilationFinal private int state_ = 1;

        private LessThanBuiltInNodeGen(TSchemeNode[] arguments) {
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state = state_;
            if ((state & 0b1101) == 0 /* only-active lessThan(long, long) */) {
                return executeBoolean_long_long0(frameValue, state);
            } else if ((state & 0b111) == 0 /* only-active lessThan(double, double) */) {
                return executeBoolean_double_double1(frameValue, state);
            } else {
                return executeBoolean_generic2(frameValue, state);
            }
        }

        private boolean executeBoolean_long_long0(VirtualFrame frameValue, int state) {
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
            assert (state & 0b10) != 0 /* is-active lessThan(long, long) */;
            return lessThan(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_double_double1(VirtualFrame frameValue, int state) {
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
            assert (state & 0b1000) != 0 /* is-active lessThan(double, double) */;
            return lessThan(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_generic2(VirtualFrame frameValue, int state) {
            Object arguments0Value_ = arguments0_.executeGeneric(frameValue);
            Object arguments1Value_ = arguments1_.executeGeneric(frameValue);
            if ((state & 0b10) != 0 /* is-active lessThan(long, long) */ && arguments0Value_ instanceof Long) {
                long arguments0Value__ = (long) arguments0Value_;
                if (arguments1Value_ instanceof Long) {
                    long arguments1Value__ = (long) arguments1Value_;
                    return lessThan(arguments0Value__, arguments1Value__);
                }
            }
            if ((state & 0b100) != 0 /* is-active lessThan(BigInteger, BigInteger) */ && TSchemeDataTypesGen.isImplicitBigInteger((state & 0b110000) >>> 4 /* extract-implicit-active 0:BigInteger */, arguments0Value_)) {
                BigInteger arguments0Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b110000) >>> 4 /* extract-implicit-active 0:BigInteger */, arguments0Value_);
                if (TSchemeDataTypesGen.isImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                    BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                    return lessThan(arguments0Value__, arguments1Value__);
                }
            }
            if ((state & 0b1000) != 0 /* is-active lessThan(double, double) */ && arguments0Value_ instanceof Double) {
                double arguments0Value__ = (double) arguments0Value_;
                if (arguments1Value_ instanceof Double) {
                    double arguments1Value__ = (double) arguments1Value_;
                    return lessThan(arguments0Value__, arguments1Value__);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            return executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
                if (arguments0Value instanceof Long) {
                    long arguments0Value_ = (long) arguments0Value;
                    if (arguments1Value instanceof Long) {
                        long arguments1Value_ = (long) arguments1Value;
                        this.state_ = state | 0b10 /* add-active lessThan(long, long) */;
                        lock.unlock();
                        hasLock = false;
                        return lessThan(arguments0Value_, arguments1Value_);
                    }
                }
                {
                    int bigIntegerCast0;
                    if ((bigIntegerCast0 = TSchemeDataTypesGen.specializeImplicitBigInteger(arguments0Value)) != 0) {
                        BigInteger arguments0Value_ = TSchemeDataTypesGen.asImplicitBigInteger(bigIntegerCast0, arguments0Value);
                        int bigIntegerCast1;
                        if ((bigIntegerCast1 = TSchemeDataTypesGen.specializeImplicitBigInteger(arguments1Value)) != 0) {
                            BigInteger arguments1Value_ = TSchemeDataTypesGen.asImplicitBigInteger(bigIntegerCast1, arguments1Value);
                            state = (state | (bigIntegerCast0 << 4) /* set-implicit-active 0:BigInteger */);
                            state = (state | (bigIntegerCast1 << 6) /* set-implicit-active 1:BigInteger */);
                            this.state_ = state | 0b100 /* add-active lessThan(BigInteger, BigInteger) */;
                            lock.unlock();
                            hasLock = false;
                            return lessThan(arguments0Value_, arguments1Value_);
                        }
                    }
                }
                if (arguments0Value instanceof Double) {
                    double arguments0Value_ = (double) arguments0Value;
                    if (arguments1Value instanceof Double) {
                        double arguments1Value_ = (double) arguments1Value;
                        this.state_ = state | 0b1000 /* add-active lessThan(double, double) */;
                        lock.unlock();
                        hasLock = false;
                        return lessThan(arguments0Value_, arguments1Value_);
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
            } else if (((state & 0b1110) & ((state & 0b1110) - 1)) == 0 /* is-single-active  */) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

    }
}
