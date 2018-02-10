package tscheme.truffle.syntax;

import tscheme.truffle.parser.Syntax;

import com.oracle.truffle.api.source.SourceSection;

public class LongIntegerSyntax extends Syntax<Long> {
	public LongIntegerSyntax(long value, SourceSection source) {
		super(value, source);
	}
}
