package tscheme.truffle.nodetypes.invocations;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.*;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.nodetypes.TSchemeNode;

import java.util.Arrays;

public class TCInvokeNode extends InvokeNode {
    @Child protected TSchemeNode functionNode;
    @Children protected final TSchemeNode[] argumentNodes;
    @Child protected IndirectCallNode callNode;

    public TCInvokeNode(TSchemeNode functionNode, TSchemeNode[] argumentNodes) {
        this.functionNode = functionNode;
        this.argumentNodes = argumentNodes;
        this.callNode = Truffle.getRuntime().createIndirectCallNode();
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
        if (this.isTail()) {
            throw new TailCallException(function.callTarget, argumentValues);
        } else {
            return this.call(virtualFrame, function.callTarget, argumentValues);
        }
    }

    private TSchemeFunction evaluateFunction(VirtualFrame virtualFrame) {
        try {
            return this.functionNode.executeTSchemeFunction(virtualFrame);
        } catch (UnexpectedResultException e) {
            throw new UnsupportedSpecializationException(this,
                    new Node[] {this.functionNode}, e);
        }
    }

    public Object call(VirtualFrame virtualFrame, CallTarget callTarget, Object[] arguments) {
        while (true) {
            try {
                return callNode.call(callTarget, arguments);
            } catch (TailCallException e) {
                callTarget = e.callTarget;
                arguments = e.arguments;
            }
        }
    }

    @Override
    public String toString() {
        return "(apply " + this.functionNode + " " +
                Arrays.toString(this.argumentNodes) + ")";
    }
}