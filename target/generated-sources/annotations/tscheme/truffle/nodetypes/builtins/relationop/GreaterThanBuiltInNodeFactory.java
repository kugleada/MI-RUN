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
import tscheme.truffle.TSchemeDataTypes;
import tscheme.truffle.TSchemeDataTypesGen;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(GreaterThanBuiltInNode.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public final class GreaterThanBuiltInNodeFactory implements NodeFactory<GreaterThanBuiltInNode> {

    private static GreaterThanBuiltInNodeFactory instance;

    private GreaterThanBuiltInNodeFactory() {
    }

    @Override
    public Class<GreaterThanBuiltInNode> getNodeClass() {
        return GreaterThanBuiltInNode.class;
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
    public GreaterThanBuiltInNode createNode(Object... arguments) {
        if (arguments.length == 1 && (arguments[0] == null || arguments[0] instanceof TSchemeNode[])) {
            return create((TSchemeNode[]) arguments[0]);
        } else {
            throw new IllegalArgumentException("Invalid create signature.");
        }
    }

    public static NodeFactory<GreaterThanBuiltInNode> getInstance() {
        if (instance == null) {
            instance = new GreaterThanBuiltInNodeFactory();
        }
        return instance;
    }

    public static GreaterThanBuiltInNode create(TSchemeNode[] arguments) {
        return new GreaterThanBuiltInNodeGen(arguments);
    }

    @GeneratedBy(GreaterThanBuiltInNode.class)
    public static final class GreaterThanBuiltInNodeGen extends GreaterThanBuiltInNode {

        @Child private TSchemeNode arguments0_;
        @Child private TSchemeNode arguments1_;
        @CompilationFinal private int state_ = 1;

        private GreaterThanBuiltInNodeGen(TSchemeNode[] arguments) {
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state = state_;
            if ((state & 0b1101) == 0 /* only-active greaterThan(long, long) */) {
                return executeBoolean_long_long0(frameValue, state);
            } else if ((state & 0b111) == 0 /* only-active greaterThan(double, double) */) {
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
            assert (state & 0b10) != 0 /* is-active greaterThan(long, long) */;
            return greaterThan(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_double_double1(VirtualFrame frameValue, int state) {
            long arguments0Value_long = 0L;
            double arguments0Value_;
            try {
                if ((state & 0b11000000001) == 0 /* only-active 0:double */) {
                    arguments0Value_long = arguments0_.executeLong(frameValue);
                    arguments0Value_ = TSchemeDataTypes.castLongToDouble(arguments0Value_long);
                } else if ((state & 0b10100000001) == 0 /* only-active 0:double */) {
                    arguments0Value_ = arguments0_.executeDouble(frameValue);
                } else {
                    Object arguments0Value__ = arguments0_.executeGeneric(frameValue);
                    arguments0Value_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000000) >>> 8 /* extract-implicit-active 0:double */, arguments0Value__);
                }
            } catch (UnexpectedResultException ex) {
                Object arguments1Value = arguments1_.executeGeneric(frameValue);
                return executeAndSpecialize(ex.getResult(), arguments1Value);
            }
            long arguments1Value_long = 0L;
            double arguments1Value_;
            try {
                if ((state & 0b11000000000001) == 0 /* only-active 1:double */) {
                    arguments1Value_long = arguments1_.executeLong(frameValue);
                    arguments1Value_ = TSchemeDataTypes.castLongToDouble(arguments1Value_long);
                } else if ((state & 0b10100000000001) == 0 /* only-active 1:double */) {
                    arguments1Value_ = arguments1_.executeDouble(frameValue);
                } else {
                    Object arguments1Value__ = arguments1_.executeGeneric(frameValue);
                    arguments1Value_ = TSchemeDataTypesGen.expectImplicitDouble((state & 0b11100000000000) >>> 11 /* extract-implicit-active 1:double */, arguments1Value__);
                }
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(((state & 0b11000000001) == 0 /* only-active 0:double */ ? (Object) arguments0Value_long : (Object) arguments0Value_), ex.getResult());
            }
            assert (state & 0b1000) != 0 /* is-active greaterThan(double, double) */;
            return greaterThan(arguments0Value_, arguments1Value_);
        }

        private boolean executeBoolean_generic2(VirtualFrame frameValue, int state) {
            Object arguments0Value_ = arguments0_.executeGeneric(frameValue);
            Object arguments1Value_ = arguments1_.executeGeneric(frameValue);
            if ((state & 0b10) != 0 /* is-active greaterThan(long, long) */ && arguments0Value_ instanceof Long) {
                long arguments0Value__ = (long) arguments0Value_;
                if (arguments1Value_ instanceof Long) {
                    long arguments1Value__ = (long) arguments1Value_;
                    return greaterThan(arguments0Value__, arguments1Value__);
                }
            }
            if ((state & 0b100) != 0 /* is-active greaterThan(BigInteger, BigInteger) */ && TSchemeDataTypesGen.isImplicitBigInteger((state & 0b110000) >>> 4 /* extract-implicit-active 0:BigInteger */, arguments0Value_)) {
                BigInteger arguments0Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b110000) >>> 4 /* extract-implicit-active 0:BigInteger */, arguments0Value_);
                if (TSchemeDataTypesGen.isImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 1:BigInteger */, arguments1Value_)) {
                    BigInteger arguments1Value__ = TSchemeDataTypesGen.asImplicitBigInteger((state & 0b11000000) >>> 6 /* extract-implicit-active 1:BigInteger */, arguments1Value_);
                    return greaterThan(arguments0Value__, arguments1Value__);
                }
            }
            if ((state & 0b1000) != 0 /* is-active greaterThan(double, double) */ && TSchemeDataTypesGen.isImplicitDouble((state & 0b11100000000) >>> 8 /* extract-implicit-active 0:double */, arguments0Value_)) {
                double arguments0Value__ = TSchemeDataTypesGen.asImplicitDouble((state & 0b11100000000) >>> 8 /* extract-implicit-active 0:double */, arguments0Value_);
                if (TSchemeDataTypesGen.isImplicitDouble((state & 0b11100000000000) >>> 11 /* extract-implicit-active 1:double */, arguments1Value_)) {
                    double arguments1Value__ = TSchemeDataTypesGen.asImplicitDouble((state & 0b11100000000000) >>> 11 /* extract-implicit-active 1:double */, arguments1Value_);
                    return greaterThan(arguments0Value__, arguments1Value__);
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
                        this.state_ = state | 0b10 /* add-active greaterThan(long, long) */;
                        lock.unlock();
                        hasLock = false;
                        return greaterThan(arguments0Value_, arguments1Value_);
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
                            this.state_ = state | 0b100 /* add-active greaterThan(BigInteger, BigInteger) */;
                            lock.unlock();
                            hasLock = false;
                            return greaterThan(arguments0Value_, arguments1Value_);
                        }
                    }
                }
                {
                    int doubleCast0;
                    if ((doubleCast0 = TSchemeDataTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
                        double arguments0Value_ = TSchemeDataTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                        int doubleCast1;
                        if ((doubleCast1 = TSchemeDataTypesGen.specializeImplicitDouble(arguments1Value)) != 0) {
                            double arguments1Value_ = TSchemeDataTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
                            state = (state | (doubleCast0 << 8) /* set-implicit-active 0:double */);
                            state = (state | (doubleCast1 << 11) /* set-implicit-active 1:double */);
                            this.state_ = state | 0b1000 /* add-active greaterThan(double, double) */;
                            lock.unlock();
                            hasLock = false;
                            return greaterThan(arguments0Value_, arguments1Value_);
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
            } else if (((state & 0b1110) & ((state & 0b1110) - 1)) == 0 /* is-single-active  */) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

    }
}
