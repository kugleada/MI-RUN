package cz.schemeinterpreter.truffleNodes.type;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import cz.schemeinterpreter.truffleNodes.node.TSchemeRootNode;

public class TSchemeFunction {

    protected RootCallTarget callTarget; //< Truffle invocations target, callTarget = Truffle.getRuntime().createCallTarget(rootNode)

    protected MaterializedFrame scope; //< scope of this function

    public final int argc; //< amount of arguments

    public TSchemeFunction(RootCallTarget callTarget, int argc) {
        this.callTarget = callTarget;
        this.argc = argc;
    }

    public RootCallTarget getCallTarget() {
        return callTarget;
    }

    public MaterializedFrame getScope() {
        return scope;
    }

    public void setScope(MaterializedFrame scope) {
        this.scope = scope;
    }
}
