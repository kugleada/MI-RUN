package cz.schemeinterpreter.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.nodes.SchemeRootNode;

public class SchemeFunction {
    public final RootCallTarget callTarget;
    private MaterializedFrame lexicalScope;

    public SchemeFunction(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    public MaterializedFrame getLexicalScope() {
        return this.lexicalScope;
    }

    public void setLexicalScope(MaterializedFrame lexicalScope) {
        this.lexicalScope = lexicalScope;
    }

    public static SchemeFunction create(FrameSlot[] arguments, SchemeNode[] bodyNodes, FrameDescriptor frameDescriptor) {
        return new SchemeFunction(
                Truffle.getRuntime().createCallTarget(SchemeRootNode.create(arguments, bodyNodes, frameDescriptor)));
    }
}