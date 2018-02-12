package tscheme.truffle.parser.syntax;

import com.oracle.truffle.api.source.SourceSection;

public class DoubleSyntax extends Syntax<Double> {
    public DoubleSyntax(Double value, SourceSection source) {
        super(value, source);
    }
}
