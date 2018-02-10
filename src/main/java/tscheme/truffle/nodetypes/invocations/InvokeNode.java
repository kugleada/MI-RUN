package tscheme.truffle.nodetypes.invocations;

import java.util.Arrays;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.datatypes.TSchemeFunction;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;

public class InvokeNode extends TSchemeNode {

    @Child protected TSchemeNode functionNode;

    @Children protected final TSchemeNode[] argumentNodes;

    @Child protected DispatchNode dispatchNode;

    public InvokeNode(TSchemeNode functionNode,
                      TSchemeNode[] argumentNodes,
            SourceSection sourceSection) {
        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
        this.dispatchNode = new UninitializedDispatchNode();
        setSourceSection(sourceSection);
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.evaluateFunction(virtualFrame);
        CompilerAsserts.compilationConstant(this.argumentNodes.length);
        CompilerAsserts.compilationConstant(this.isTail());

        Object[] argumentValues = new Object[this.argumentNodes.length + 1];
        argumentValues[0] = function.getLexicalScope();
        for (int i=0; i<this.argumentNodes.length; i++) {
            argumentValues[i+1] = this.argumentNodes[i].executeGeneric(virtualFrame);
        }

        return call(virtualFrame, function.callTarget, argumentValues);
    }

    protected Object call(VirtualFrame virtualFrame, CallTarget callTarget,
            Object[] arguments) {
        return this.dispatchNode.executeDispatch(virtualFrame,
                callTarget, arguments);
    }

    private TSchemeFunction evaluateFunction(VirtualFrame virtualFrame) {
        try {
            return this.functionNode.executeTSchemeFunction(virtualFrame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this,
                    new Node[] {this.functionNode}, e);
        }
    }

    @Override
    public String toString() {
        return "(apply " + this.functionNode + " " +
                Arrays.toString(this.argumentNodes) + ")";
    }
}
