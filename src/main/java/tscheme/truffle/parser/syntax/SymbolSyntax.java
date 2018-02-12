package tscheme.truffle.parser.syntax;

import com.oracle.truffle.api.source.SourceSection;

import tscheme.truffle.datatypes.TSchemeSymbol;

public class SymbolSyntax extends Syntax<TSchemeSymbol> {
	public SymbolSyntax(TSchemeSymbol value, SourceSection source) {
		super(value, source);
	}
}