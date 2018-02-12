package tscheme.truffle.parser.syntax;

import com.oracle.truffle.api.source.SourceSection;

public class BooleanSyntax extends Syntax<Boolean> {
	public BooleanSyntax(boolean value, SourceSection source) {
		super(value, source);
	}
}
