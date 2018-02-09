package cz.schemeinterpreter.nodes.special;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.types.SchemeFunction;

@NodeField(name = "function", type = SchemeFunction.class)
public abstract class LambdaNode extends SchemeNode {
    public abstract SchemeFunction getFunction();

    private boolean scopeSet = false;

    @Specialization(guards = "isScopeSet()")
    public SchemeFunction getScopedFunction(VirtualFrame virtualFrame) {
        return this.getFunction();
    }

    @Specialization(replaces = {"getScopedFunction"})
    public Object getSchemeFunction(VirtualFrame virtualFrame) {
        SchemeFunction function = this.getFunction();
        function.setLexicalScope(virtualFrame.materialize());
        return function;
    }

    protected boolean isScopeSet() {
        return this.scopeSet;
    }

    public static SchemeFunction createSchemeFunction(RootNode rootNode, VirtualFrame currentFrame) {
        return new SchemeFunction(Truffle.getRuntime().createCallTarget(rootNode));
    }
}
