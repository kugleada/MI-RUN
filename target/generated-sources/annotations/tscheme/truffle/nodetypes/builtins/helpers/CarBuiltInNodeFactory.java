// CheckStyle: start generated
package tscheme.truffle.nodetypes.builtins.helpers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.nodetypes.TSchemeNode;

@GeneratedBy(CarBuiltInNode.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public final class CarBuiltInNodeFactory implements NodeFactory<CarBuiltInNode> {

    private static CarBuiltInNodeFactory instance;

    private CarBuiltInNodeFactory() {
    }

    @Override
    public Class<CarBuiltInNode> getNodeClass() {
        return CarBuiltInNode.class;
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
    public CarBuiltInNode createNode(Object... arguments) {
        if (arguments.length == 1 && (arguments[0] == null || arguments[0] instanceof TSchemeNode[])) {
            return create((TSchemeNode[]) arguments[0]);
        } else {
            throw new IllegalArgumentException("Invalid create signature.");
        }
    }

    public static NodeFactory<CarBuiltInNode> getInstance() {
        if (instance == null) {
            instance = new CarBuiltInNodeFactory();
        }
        return instance;
    }

    public static CarBuiltInNode create(TSchemeNode[] arguments) {
        return new CarBuiltInNodeGen(arguments);
    }

    @GeneratedBy(CarBuiltInNode.class)
    public static final class CarBuiltInNodeGen extends CarBuiltInNode {

        @Child private TSchemeNode arguments0_;
        @CompilationFinal private int state_ = 1;

        private CarBuiltInNodeGen(TSchemeNode[] arguments) {
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            Object arguments0Value_ = arguments0_.executeGeneric(frameValue);
            if ((state & 0b10) != 0 /* is-active car(TSchemeList<>) */ && arguments0Value_ instanceof TSchemeList<?>) {
                TSchemeList<?> arguments0Value__ = (TSchemeList<?>) arguments0Value_;
                return car(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(arguments0Value_);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state = state_ & 0xfffffffe/* mask-active uninitialized*/;
                if (arguments0Value instanceof TSchemeList<?>) {
                    TSchemeList<?> arguments0Value_ = (TSchemeList<?>) arguments0Value;
                    this.state_ = state | 0b10 /* add-active car(TSchemeList<>) */;
                    lock.unlock();
                    hasLock = false;
                    return car(arguments0Value_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new UnsupportedSpecializationException(this, new Node[] {this.arguments0_}, arguments0Value);
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
            } else {
                return NodeCost.MONOMORPHIC;
            }
        }

    }
}
