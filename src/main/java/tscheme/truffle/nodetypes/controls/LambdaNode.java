package tscheme.truffle.nodetypes.controls;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;
import tscheme.truffle.datatypes.TSchemeFunction;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

/**
 * Represents lambda function, represented as node in AST.
 */
@NodeField(name = "function", type = TSchemeFunction.class)
public abstract class LambdaNode extends TSchemeNode {

    public abstract TSchemeFunction getFunction(); //< get function that is represented by this lambda

    private boolean scopeSetFlag = false; //< flag, checks if scope of this function is set
    protected boolean isScopeSet() {
        return this.scopeSetFlag;
    }

    @Specialization(guards = "isScopeSet()")
    public TSchemeFunction getFunctionWithScope(VirtualFrame virtualFrame) {
        return this.getFunction();
    }

    @Specialization(replaces = { "getFunctionWithScope" })
    public Object getMumblerFunction(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.getFunction();
        function.setLexicalScope(virtualFrame.materialize()); // materialized frame, VF can't be accessed from heap
        return function;
    }

    /**
     * Set name to this lambda.
     */
    public void setName(String name) {
        ((TSchemeRootNode) this.getFunction().callTarget.getRootNode())
            .setName(name);
    }
}
