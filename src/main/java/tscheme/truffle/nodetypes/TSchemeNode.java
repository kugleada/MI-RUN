package tscheme.truffle.nodetypes;

import java.math.BigInteger;

import tscheme.truffle.TSchemeDataTypes;
import tscheme.truffle.TSchemeDataTypesGen;
import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.source.SourceSection;

@TypeSystemReference(TSchemeDataTypes.class)
@NodeInfo(language = "TScheme language", description = "Abstract base node.")
public abstract class TSchemeNode extends Node {

    public abstract Object executeGeneric(VirtualFrame virtualFrame);

    public long executeLong(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectLong(this.executeGeneric(virtualFrame));
    }

    public double executeDouble(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectDouble(this.executeGeneric(virtualFrame));
    }

    public boolean executeBoolean(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectBoolean(this.executeGeneric(virtualFrame));
    }

    public BigInteger executeBigInteger(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectBigInteger(this.executeGeneric(virtualFrame));
    }

    public TSchemeSymbol executeTSchemeSymbol(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeSymbol(this.executeGeneric(virtualFrame));
    }

    public TSchemeFunction executeTSchemeFunction(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeFunction(
                this.executeGeneric(virtualFrame));
    }

    public TSchemeList<?> executeTSchemeList(VirtualFrame virtualFrame)
            throws UnexpectedResultException {
        return TSchemeDataTypesGen.expectTSchemeList(this.executeGeneric(virtualFrame));
    }

    public String executeString(VirtualFrame virtualFrame)
            throws UnexpectedResultException{
        return TSchemeDataTypesGen.expectString(this.executeGeneric(virtualFrame));
    }

    protected boolean isArgumentIndexInRange(VirtualFrame virtualFrame,
            int index) {
        return (index + 1) < virtualFrame.getArguments().length;
    }

    protected Object getArgument(VirtualFrame virtualFrame, int index) {
        return virtualFrame.getArguments()[index + 1];
    }

    protected static MaterializedFrame getLexicalScope(Frame frame) {
        Object[] args = frame.getArguments();
        if (args.length > 0) {
            return (MaterializedFrame) frame.getArguments()[0];
        } else {
            return null;
        }
    }
}
