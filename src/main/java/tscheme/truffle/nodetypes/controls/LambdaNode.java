package tscheme.truffle.nodetypes.controls;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;
import tscheme.truffle.datatypes.TSchemeFunction;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeField(name = "function", type = TSchemeFunction.class)
public abstract class LambdaNode extends TSchemeNode {
    public abstract TSchemeFunction getFunction();

    private boolean scopeSet = false;

    protected boolean isScopeSet() {
        return this.scopeSet;
    }

    @Specialization(guards = "isScopeSet()")
    public TSchemeFunction getScopedFunction(VirtualFrame virtualFrame) {
        return this.getFunction();
    }

    @Specialization(replaces = { "getScopedFunction" })
    public Object getMumblerFunction(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.getFunction();
        function.setLexicalScope(virtualFrame.materialize());
        return function;
    }

    /**
     * The lambda expression is being set to a variable. Give the lambda the
     * name of the variable. Don't overwrite lambdas with existing names.
     *
     * @param name The name for the lambda.
     */
    public void setName(String name) {
        ((TSchemeRootNode) this.getFunction().callTarget.getRootNode())
            .setName(name);
    }
}
