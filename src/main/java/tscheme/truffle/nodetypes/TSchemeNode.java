package tscheme.truffle.nodetypes;

import java.math.BigInteger;

import tscheme.truffle.datatypes.TSchemeDataTypes;
import tscheme.truffle.datatypes.TSchemeDataTypesGen;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

/**
 * Class represents a node in AST.
 */
@TypeSystemReference(TSchemeDataTypes.class)
@NodeInfo(language = "TScheme language", description = "Abstract base node.")
public abstract class TSchemeNode extends Node {

    public abstract Object executeGeneric(VirtualFrame virtualFrame);

    // Execute basic types
    public long executeLong(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectLong(this.executeGeneric(virtualFrame));
    }

    public boolean executeBoolean(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectBoolean(this.executeGeneric(virtualFrame));
    }

    public BigInteger executeBigInteger(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectBigInteger(this.executeGeneric(virtualFrame));
    }

    public double executeDouble(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectDouble(this.executeGeneric(virtualFrame));
    }

    public TSchemeSymbol executeTSchemeSymbol(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeSymbol(this.executeGeneric(virtualFrame));
    }

    // Execute function
    public TSchemeFunction executeTSchemeFunction(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeFunction(
                this.executeGeneric(virtualFrame));
    }

    // Execute list
    public TSchemeList<?> executeTSchemeList(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeList(this.executeGeneric(virtualFrame));
    }

    // Execute String
    public String executeString(VirtualFrame virtualFrame)
            throws UnexpectedResultException{
        return TSchemeDataTypesGen.expectString(this.executeGeneric(virtualFrame));
    }

    /**
     * Check if index of argument is within range of frame's arguments.
     * @param virtualFrame VF with desired argument.
     * @param index Index of argument to be checked.
     * @return True if OK, false if not ok.
     */
    protected boolean isArgumentIndexInRange(VirtualFrame virtualFrame,
            int index) {
        return (index + 1) < virtualFrame.getArguments().length;
    }

    /**
     * Getter for argument from given frame.
     * @param virtualFrame Frame with desired argument.
     * @param index Index of argument within given frame.
     * @return Return the Object on given index in given frame.
     */
    protected Object getArgument(VirtualFrame virtualFrame, int index) {
        return virtualFrame.getArguments()[index + 1];
    }

    /**
     * Get LexicalScope of this Node.
     * @param frame Frame that contains scope (just arguments).
     * @return Return materialized frame with given arguments (because we want to access things in frame).
     */
    protected static MaterializedFrame getLexicalScope(Frame frame) {
        Object[] args = frame.getArguments();
        if (args.length > 0) {
            return (MaterializedFrame) frame.getArguments()[0]; // return materialized so we can access variables elsewhere
        } else {
            return null; // nothing in frame
        }
    }
}
