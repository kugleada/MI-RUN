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
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(function.callTarget));
        }

        if (function.callTarget != this.callNode.getCallTarget()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnsupportedOperationException("need to implement a proper inline cache.");
        }

        return callNode.call(argumentValues);
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


/*
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

    @Child
    protected TSchemeNode functionNode;

    @Children
    protected final TSchemeNode[] argumentNodes;

    @Child
    protected DispatchNode dispatchNode;

    public InvokeNode(TSchemeNode functionNode,
                      TSchemeNode[] argumentNodes) {

        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
        this.dispatchNode = new UninitializedDispatchNode();
    }

    @Override
    public String toString() {
        return "InvokeNode: (apply " + this.functionNode + " " +
                Arrays.toString(this.argumentNodes) + ")";
    }

    @Override
    @ExplodeLoop
    public Object executeGeneric(VirtualFrame virtualFrame) {
        TSchemeFunction function = this.evaluateFunction(virtualFrame);
        CompilerAsserts.compilationConstant(this.argumentNodes.length);

        System.out.println("Invoke node:");
        System.out.println(this);
        System.out.println("Args:");

        Object[] argumentValues = new Object[this.argumentNodes.length + 1];
        argumentValues[0] = function.getLexicalScope();
        for (int i=0; i<this.argumentNodes.length; i++) {
            //System.out.println(this.argumentNodes[i]);
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

}
*/
