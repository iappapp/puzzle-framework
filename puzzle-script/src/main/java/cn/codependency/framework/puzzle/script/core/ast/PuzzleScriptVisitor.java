// Generated from /Users/echo/Documents/workspace/projects/puzzle/puzzle-script/src/main/java/cn/codependency/framework/puzzle/script/core/antlr/PuzzleScript.g4 by ANTLR 4.13.2

package cn.codependency.framework.puzzle.script.core.ast;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PuzzleScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PuzzleScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#infinite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfinite(PuzzleScriptParser.InfiniteContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#headers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaders(PuzzleScriptParser.HeadersContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(PuzzleScriptParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#filters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilters(PuzzleScriptParser.FiltersContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(PuzzleScriptParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(PuzzleScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PuzzleScriptParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#callFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallFunction(PuzzleScriptParser.CallFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#groupExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupExpr(PuzzleScriptParser.GroupExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#callTask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallTask(PuzzleScriptParser.CallTaskContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(PuzzleScriptParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#whenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenExpression(PuzzleScriptParser.WhenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#whenStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenStatements(PuzzleScriptParser.WhenStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#whenThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenThenStatement(PuzzleScriptParser.WhenThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#defaultStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultStatement(PuzzleScriptParser.DefaultStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(PuzzleScriptParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenWopThenStatements(PuzzleScriptParser.WhenWopThenStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenWopThenStatement(PuzzleScriptParser.WhenWopThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(PuzzleScriptParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#stringItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringItem(PuzzleScriptParser.StringItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(PuzzleScriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(PuzzleScriptParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link PuzzleScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(PuzzleScriptParser.FloatLiteralContext ctx);
}