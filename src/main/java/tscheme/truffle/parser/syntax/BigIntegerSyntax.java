package tscheme.truffle.parser.syntax;

import java.math.BigInteger;

import com.oracle.truffle.api.source.SourceSection;

public class BigIntegerSyntax extends Syntax<BigInteger> {
	public BigIntegerSyntax(BigInteger value, SourceSection source) {
		super(value, source);
	}
}
