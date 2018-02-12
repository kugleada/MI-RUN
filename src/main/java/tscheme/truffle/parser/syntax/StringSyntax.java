package tscheme.truffle.parser.syntax;

import com.oracle.truffle.api.source.SourceSection;

public class StringSyntax extends Syntax<String> {
	public StringSyntax(String value, SourceSection source) {
		super(value, source);
	}
}
