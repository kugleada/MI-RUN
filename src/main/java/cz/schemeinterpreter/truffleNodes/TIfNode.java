package cz.schemeinterpreter.truffleNodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import cz.schemeinterpreter.truffleNodes.type.TSchemeList;

public class TIfNode extends TSchemeNode {
    @Child private TSchemeNode testNode;
    @Child private TSchemeNode thenNode;
    @Child private TSchemeNode elseNode;

    private final ConditionProfile conditionProfile =
            ConditionProfile.createBinaryProfile();

    public TIfNode(TSchemeNode testNode, TSchemeNode thenNode,
                   TSchemeNode elseNode) {
        this.testNode = testNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        if (this.conditionProfile.profile(this.testResult(virtualFrame))) {
            return this.thenNode.execute(virtualFrame);
        } else {
            return this.elseNode.execute(virtualFrame);
        }
    }

    private boolean testResult(VirtualFrame virtualFrame) {
        try {
            return this.testNode.executeBoolean(virtualFrame);
        } catch (UnexpectedResultException e) {
            Object result = this.testNode.execute(virtualFrame);
            return result != TSchemeList.EMPTY;
        }
    }
}