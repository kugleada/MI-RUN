package tscheme.truffle.parser.syntax;

import com.oracle.truffle.api.source.SourceSection;

public class LongIntegerSyntax extends Syntax<Long> {
	public LongIntegerSyntax(long value, SourceSection source) {
		super(value, source);
	}
}
