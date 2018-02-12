package tscheme.truffle.nodetypes.invocations;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.*;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.nodetypes.TSchemeNode;

import java.util.Arrays;

/**
 * Invocation node which uses DirectCallNode instead of IndirectCallNode (it should be faster).
 * Targets on these invocation nodes should not be changed.
 */
public class DirectInvokeNode extends InvokeNode {
    @Child protected TSchemeNode functionNode;
    @Children protected final TSchemeNode[] argumentNodes;
    @Child protected DirectCallNode callNode;

    public DirectInvokeNode(TSchemeNode functionNode, TSchemeNode[] argumentNodes) {
        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
        this.callNode = null;
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.evaluateFunction(virtualFrame);
        CompilerAsserts.compilationConstant(this.argumentNodes.length);

        Object[] argumentValues = new Object[this.argumentNodes.length + 1];
        argumentValues[0] = function.getLexicalScope();
        for (int i=0; i<this.argumentNodes.length; i++) {
            argumentValues[i+1] = this.argumentNodes[i].executeGeneric(virtualFrame);
        }

        if (this.callNode == null)  {
            //set DirectCallNode in runtime
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(function.callTarget));
        }

        if (function.callTarget != this.callNode.getCallTarget()) {
            //each InvokeNode has its own target so this should not happen
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnsupportedOperationException("need to implement a proper inline cache.");
        }

        return callNode.call(argumentValues);
    }

    private TSchemeFunction evaluateFunction(VirtualFrame virtualFrame) {
        //there should be function on the first place in list
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

/*
//invokeNode with IndirectCall
public class InvokeNode extends TSchemeNode {
    @Child protected TSchemeNode functionNode;
    @Children protected final TSchemeNode[] argumentNodes;
    @Child protected IndirectCallNode callNode;

    public InvokeNode(TSchemeNode functionNode, TSchemeNode[] argumentNodes) {
        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.evaluateFunction(virtualFrame);
        CompilerAsserts.compilationConstant(this.argumentNodes.length);

        Object[] argumentValues = new Object[this.argumentNodes.length + 1];
        argumentValues[0] = function.getLexicalScope();
        for (int i=0; i<this.argumentNodes.length; i++) {
            argumentValues[i+1] = this.argumentNodes[i].executeGeneric(virtualFrame);
        }

        return callNode.call(function.callTarget, argumentValues);
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
*/