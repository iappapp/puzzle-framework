// Generated from /Users/echo/Documents/workspace/projects/puzzle/puzzle-script/src/main/java/cn/codependency/framework/puzzle/script/core/antlr/PuzzleScript.g4 by ANTLR 4.13.2

package cn.codependency.framework.puzzle.script.core.ast;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PuzzleScriptParser}.
 */
public interface PuzzleScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#infinite}.
	 * @param ctx the parse tree
	 */
	void enterInfinite(PuzzleScriptParser.InfiniteContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#infinite}.
	 * @param ctx the parse tree
	 */
	void exitInfinite(PuzzleScriptParser.InfiniteContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#headers}.
	 * @param ctx the parse tree
	 */
	void enterHeaders(PuzzleScriptParser.HeadersContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#headers}.
	 * @param ctx the parse tree
	 */
	void exitHeaders(PuzzleScriptParser.HeadersContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(PuzzleScriptParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(PuzzleScriptParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#filters}.
	 * @param ctx the parse tree
	 */
	void enterFilters(PuzzleScriptParser.FiltersContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#filters}.
	 * @param ctx the parse tree
	 */
	void exitFilters(PuzzleScriptParser.FiltersContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(PuzzleScriptParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(PuzzleScriptParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(PuzzleScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(PuzzleScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(PuzzleScriptParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(PuzzleScriptParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#callFunction}.
	 * @param ctx the parse tree
	 */
	void enterCallFunction(PuzzleScriptParser.CallFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#callFunction}.
	 * @param ctx the parse tree
	 */
	void exitCallFunction(PuzzleScriptParser.CallFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#groupExpr}.
	 * @param ctx the parse tree
	 */
	void enterGroupExpr(PuzzleScriptParser.GroupExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#groupExpr}.
	 * @param ctx the parse tree
	 */
	void exitGroupExpr(PuzzleScriptParser.GroupExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#callTask}.
	 * @param ctx the parse tree
	 */
	void enterCallTask(PuzzleScriptParser.CallTaskContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#callTask}.
	 * @param ctx the parse tree
	 */
	void exitCallTask(PuzzleScriptParser.CallTaskContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(PuzzleScriptParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(PuzzleScriptParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhenExpression(PuzzleScriptParser.WhenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhenExpression(PuzzleScriptParser.WhenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#whenStatements}.
	 * @param ctx the parse tree
	 */
	void enterWhenStatements(PuzzleScriptParser.WhenStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#whenStatements}.
	 * @param ctx the parse tree
	 */
	void exitWhenStatements(PuzzleScriptParser.WhenStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#whenThenStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhenThenStatement(PuzzleScriptParser.WhenThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#whenThenStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhenThenStatement(PuzzleScriptParser.WhenThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#defaultStatement}.
	 * @param ctx the parse tree
	 */
	void enterDefaultStatement(PuzzleScriptParser.DefaultStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#defaultStatement}.
	 * @param ctx the parse tree
	 */
	void exitDefaultStatement(PuzzleScriptParser.DefaultStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(PuzzleScriptParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(PuzzleScriptParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatements}.
	 * @param ctx the parse tree
	 */
	void enterWhenWopThenStatements(PuzzleScriptParser.WhenWopThenStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatements}.
	 * @param ctx the parse tree
	 */
	void exitWhenWopThenStatements(PuzzleScriptParser.WhenWopThenStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhenWopThenStatement(PuzzleScriptParser.WhenWopThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#whenWopThenStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhenWopThenStatement(PuzzleScriptParser.WhenWopThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(PuzzleScriptParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(PuzzleScriptParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#stringItem}.
	 * @param ctx the parse tree
	 */
	void enterStringItem(PuzzleScriptParser.StringItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#stringItem}.
	 * @param ctx the parse tree
	 */
	void exitStringItem(PuzzleScriptParser.StringItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(PuzzleScriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(PuzzleScriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(PuzzleScriptParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(PuzzleScriptParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuzzleScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(PuzzleScriptParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuzzleScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(PuzzleScriptParser.FloatLiteralContext ctx);
}