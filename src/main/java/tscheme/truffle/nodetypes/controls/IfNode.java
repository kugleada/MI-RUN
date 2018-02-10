package tscheme.truffle.nodetypes.controls;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;

public class IfNode extends TSchemeNode {

    @Child private TSchemeNode testNode;
    @Child private TSchemeNode thenNode;
    @Child private TSchemeNode elseNode;

    private final ConditionProfile conditionProfile =
            ConditionProfile.createBinaryProfile();

    public IfNode(TSchemeNode testNode, TSchemeNode thenNode,
                  TSchemeNode elseNode, SourceSection sourceSection) {
        this.testNode = testNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
        setSourceSection(sourceSection);
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        // Condition profile should make processing of if faster (preference of more frequently used branch).
        if (this.conditionProfile.profile(this.testResult(virtualFrame))) {
            return this.thenNode.executeGeneric(virtualFrame);
        } else {
            return this.elseNode.executeGeneric(virtualFrame);
        }
    }

    @Override
    public void setIsTail() {
        super.setIsTail();
        this.thenNode.setIsTail();
        this.elseNode.setIsTail();
    }

    private boolean testResult(VirtualFrame virtualFrame) {
        try {
            return this.testNode.executeBoolean(virtualFrame);
        } catch (UnexpectedResultException e) {
            Object result = this.testNode.executeGeneric(virtualFrame);
            return result != TSchemeList.EMPTY;
        }
    }

    @Override
    public String toString() {
        return "(if " + this.testNode + " " + this.thenNode + " " + this.elseNode + ")";
    }
}
