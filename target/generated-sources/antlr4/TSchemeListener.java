// Generated from TScheme.g4 by ANTLR 4.7

    package tscheme.truffle.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TSchemeParser}.
 */
public interface TSchemeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TSchemeParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(TSchemeParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link TSchemeParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(TSchemeParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code list}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterList(TSchemeParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code list}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitList(TSchemeParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code quote}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterQuote(TSchemeParser.QuoteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code quote}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitQuote(TSchemeParser.QuoteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterNumber(TSchemeParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitNumber(TSchemeParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code double}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterDouble(TSchemeParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code double}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitDouble(TSchemeParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterBool(TSchemeParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitBool(TSchemeParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterString(TSchemeParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitString(TSchemeParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code symbol}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void enterSymbol(TSchemeParser.SymbolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code symbol}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 */
	void exitSymbol(TSchemeParser.SymbolContext ctx);
}