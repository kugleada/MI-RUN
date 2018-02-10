package tscheme.truffle.syntax;

import com.oracle.truffle.api.source.SourceSection;
import tscheme.truffle.parser.Syntax;

public class DoubleSyntax extends Syntax<Double> {
    public DoubleSyntax(Double value, SourceSection source) {
        super(value, source);
    }
}
