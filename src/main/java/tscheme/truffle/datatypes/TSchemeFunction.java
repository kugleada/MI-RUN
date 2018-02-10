package tscheme.truffle.datatypes;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;

/**
 * Represents function - callable object.
 */
public class TSchemeFunction {

    public final RootCallTarget callTarget; //< WHAT should be called, "root of a tree".

    private MaterializedFrame lexicalScope; //< lexical scope of THIS function

    /**
     * Constructors for function.
     * @param callTarget What should be called.
     */
    public TSchemeFunction(RootCallTarget callTarget) {
        this.callTarget = callTarget;
    }

    /**
     * Creates function along with its root node.
     * @param arguments Function args.
     * @param bodyNodes Function body nodes.
     * @param frameDescriptor Frame Descriptor.
     */
    public TSchemeFunction (
            FrameSlot[] arguments,
            TSchemeNode[] bodyNodes,
            FrameDescriptor frameDescriptor)
    {
        this.callTarget = Truffle.getRuntime()
                .createCallTarget(
                        TSchemeRootNode.create(
                                arguments,
                                bodyNodes,
                                frameDescriptor));

    }

    public MaterializedFrame getLexicalScope() {
        return this.lexicalScope;
    }

    public void setLexicalScope(MaterializedFrame lexicalScope) {
        this.lexicalScope = lexicalScope;
    }

}
