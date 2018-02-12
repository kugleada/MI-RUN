// CheckStyle: start generated
package tscheme.truffle.nodetypes.builtins.print;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.datatypes.TSchemeDataTypesGen;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(PrintVarBuiltInNode.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public final class PrintVarBuiltInNodeFactory implements NodeFactory<PrintVarBuiltInNode> {

    private static PrintVarBuiltInNodeFactory instance;

    private PrintVarBuiltInNodeFactory() {
    }

    @Override
    public Class<PrintVarBuiltInNode> getNodeClass() {
        return PrintVarBuiltInNode.class;
    }

    @Override
    public List getExecutionSignature() {
        return Arrays.asList(TSchemeNode.class);
    }

    @Override
    public List getNodeSignatures() {
        return Arrays.asList(Arrays.asList(TSchemeNode[].class));
    }

    @Override
    public PrintVarBuiltInNode createNode(Object... arguments) {
        if (arguments.length == 1 && (arguments[0] == null || arguments[0] instanceof TSchemeNode[])) {
            return create((TSchemeNode[]) arguments[0]);
        } else {
            throw new IllegalArgumentException("Invalid create signature.");
        }
    }

    public static NodeFactory<PrintVarBuiltInNode> getInstance() {
        if (instance == null) {
            instance = new PrintVarBuiltInNodeFactory();
        }
        return instance;
    }

    public static PrintVarBuiltInNode create(TSchemeNode[] arguments) {
        return new PrintVarBuiltInNodeGen(arguments);
    }

    @GeneratedBy(PrintVarBuiltInNode.class)
    public static final class PrintVarBuiltInNodeGen extends PrintVarBuiltInNode {

        @Child private TSchemeNode arguments0_;
        @CompilationFinal private int state_ = 1;

        private PrintVarBuiltInNodeGen(TSchemeNode[] arguments) {
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            if ((state & 0b11101) == 0 /* only-active printvar(long) */) {
                return executeGeneric_long0(frameValue, state);
            } else if ((state & 0b11011) == 0 /* only-active printvar(boolean) */) {
                return executeGeneric_boolean1(frameValue, state);
            } else if ((state & 0b10111) == 0 /* only-active printvar(double) */) {
                return executeGeneric_double2(frameValue, state);
            } else {
                return executeGeneric_generic3(frameValue, state);
            }
        }

        private Object executeGeneric_long0(VirtualFrame frameValue, int state) {
            long arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(ex.getResult());
            }
            assert (state & 0b10) != 0 /* is-active printvar(long) */;
            return printvar(arguments0Value_);
        }

        private Object executeGeneric_boolean1(VirtualFrame frameValue, int state) {
            boolean arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeBoolean(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(ex.getResult());
            }
            assert (state & 0b100) != 0 /* is-active printvar(boolean) */;
            return printvar(arguments0Value_);
        }

        private Object executeGeneric_double2(VirtualFrame frameValue, int state) {
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                return executeAndSpecialize(ex.getResult());
            }
            assert (state & 0b1000) != 0 /* is-active printvar(double) */;
            return printvar(arguments0Value_);
        }

        private Object executeGeneric_generic3(VirtualFrame frameValue, int state) {
            Object arguments0Value_ = arguments0_.executeGeneric(frameValue);
            if ((state & 0b10) != 0 /* is-active printvar(long) */ && arguments0Value_ instanceof Long) {
                long arguments0Value__ = (long) arguments0Value_;
                return printvar(arguments0Value__);
            }
            if ((state & 0b100) != 0 /* is-active printvar(boolean) */ && arguments0Value_ instanceof Boolean) {
                boolean arguments0Value__ = (boolean) arguments0Value_;
                return printvar(arguments0Value__);
            }
            if ((state & 0b1000) != 0 /* is-active printvar(double) */ && arguments0Value_ instanceof Double) {
                double arguments0Value__ = (double) arguments0Value_;
                return printvar(arguments0Value__);
            }
            if ((state & 0b10000) != 0 /* is-active printvar(Object) */) {
                return printvar(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state = state_;
            if ((state & 0b10000) != 0 /* is-active printvar(Object) */) {
                return TSchemeDataTypesGen.expectBoolean(executeGeneric(frameValue));
            }
            boolean arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeBoolean(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(ex.getResult()));
            }
            if ((state & 0b100) != 0 /* is-active printvar(boolean) */) {
                return printvar(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TSchemeDataTypesGen.expectBoolean(executeAndSpecialize(arguments0Value_));
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state = state_;
            if ((state & 0b10000) != 0 /* is-active printvar(Object) */) {
                return TSchemeDataTypesGen.expectDouble(executeGeneric(frameValue));
            }
            double arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeDouble(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(ex.getResult()));
            }
            if ((state & 0b1000) != 0 /* is-active printvar(double) */) {
                return printvar(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TSchemeDataTypesGen.expectDouble(executeAndSpecialize(arguments0Value_));
        }

        @Override
        public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
            int state = state_;
            if ((state & 0b10000) != 0 /* is-active printvar(Object) */) {
                return TSchemeDataTypesGen.expectLong(executeGeneric(frameValue));
            }
            long arguments0Value_;
            try {
                arguments0Value_ = arguments0_.executeLong(frameValue);
            } catch (UnexpectedResultException ex) {
                return TSchemeDataTypesGen.expectLong(executeAndSpecialize(ex.getResult()));
            }
            if ((state & 0b10) != 0 /* is-active printvar(long) */) {
                return printvar(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return TSchemeDataTypesGen.expectLong(executeAndSpecialize(arguments0Value_));
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
                if (arguments0Value instanceof Long) {
                    long arguments0Value_ = (long) arguments0Value;
                    this.state_ = state | 0b10 /* add-active printvar(long) */;
                    lock.unlock();
                    hasLock = false;
                    return printvar(arguments0Value_);
                }
                if (arguments0Value instanceof Boolean) {
                    boolean arguments0Value_ = (boolean) arguments0Value;
                    this.state_ = state | 0b100 /* add-active printvar(boolean) */;
                    lock.unlock();
                    hasLock = false;
                    return printvar(arguments0Value_);
                }
                if (arguments0Value instanceof Double) {
                    double arguments0Value_ = (double) arguments0Value;
                    this.state_ = state | 0b1000 /* add-active printvar(double) */;
                    lock.unlock();
                    hasLock = false;
                    return printvar(arguments0Value_);
                }
                this.state_ = state | 0b10000 /* add-active printvar(Object) */;
                lock.unlock();
                hasLock = false;
                return printvar(arguments0Value);
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

    }
}
