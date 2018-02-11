package tscheme.truffle.nodetypes.controls;

import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;

/**
 * Represents if-then-else, syntactically (if (condition) (then) (else)).
 */
public class IfNode extends TSchemeNode {

    @Child private TSchemeNode testNode; //< Test part of IF-then-else.

    @Child private TSchemeNode thenNode; //< Then part of if-THEN-else.

    @Child private TSchemeNode elseNode; // Else part of if-then-ELSE.

    @Override
    public String toString() {
        return "IfNode: (if " + this.testNode + " " + this.thenNode + " " + this.elseNode + ")";
    }

    /*
     Condition profile should make this faster - it will prioritize branches that occur more often
     and lowers amount of rewrites (replacements) in AST.
      */
    private final ConditionProfile conditionProfile =
            ConditionProfile.createBinaryProfile();

    /**
     * Constructor for if-then-else.
     * @param testNode Condition body.
     * @param thenNode Then body.
     * @param elseNode Else body.
     * @param sourceSection Which part of source code represents this if.
     */
    public IfNode(TSchemeNode testNode, TSchemeNode thenNode,
                  TSchemeNode elseNode) {
        this.testNode = testNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    /**
     * Executes this if-then-else.
     * @param virtualFrame
     * @return Result of executed branch.
     */
    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        // Based on profiling, execute then or else bodies.
        if (this.conditionProfile.profile(this.testCondition(virtualFrame))) {
            return this.thenNode.executeGeneric(virtualFrame);
        } else {
            return this.elseNode.executeGeneric(virtualFrame);
        }
    }

    /**
     * Tests condition.
     * @param virtualFrame
     * @return True/false - result of condition.
     */
    private boolean testCondition(VirtualFrame virtualFrame) {
        try {
            // Assume it is boolean - mostly it is.
            return this.testNode.executeBoolean(virtualFrame);
        } catch (UnexpectedResultException e) {
            // If not boolean, execute generic on the same object - Truffle will handle it.
            Object result = this.testNode.executeGeneric(virtualFrame);
            return result != TSchemeList.EMPTY;
        }
    }

}
