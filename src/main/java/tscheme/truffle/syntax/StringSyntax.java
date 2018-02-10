package tscheme.truffle.syntax;

import com.oracle.truffle.api.source.SourceSection;

import tscheme.truffle.parser.Syntax;

public class StringSyntax extends Syntax<String> {
	public StringSyntax(String value, SourceSection source) {
		super(value, source);
	}
}
