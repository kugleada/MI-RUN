// Generated from TScheme.g4 by ANTLR 4.7

    package tscheme.truffle.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TSchemeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TSchemeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TSchemeParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(TSchemeParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code list}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(TSchemeParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code quote}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuote(TSchemeParser.QuoteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(TSchemeParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code double}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble(TSchemeParser.DoubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bool}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(TSchemeParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(TSchemeParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code symbol}
	 * labeled alternative in {@link TSchemeParser#form}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(TSchemeParser.SymbolContext ctx);
}