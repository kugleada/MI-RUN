package cz.schemeinterpreter.nodes.special;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.types.SchemeList;

public class IfNode extends SchemeNode {
    @Child private SchemeNode testNode;
    @Child private SchemeNode thenNode;
    @Child private SchemeNode elseNode;

    private final ConditionProfile conditionProfile =
            ConditionProfile.createBinaryProfile();

    public IfNode(SchemeNode testNode, SchemeNode thenNode,
                  SchemeNode elseNode) {
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
            return result != SchemeList.EMPTY;
        }
    }
}
