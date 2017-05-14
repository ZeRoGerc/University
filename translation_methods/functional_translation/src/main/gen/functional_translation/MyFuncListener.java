// Generated from /Users/zerogerc/Documents/University/repository/University/translation_methods/functional_translation/src/main/java/functional_translation/MyFunc.g4 by ANTLR 4.7
package functional_translation;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyFuncParser}.
 */
public interface MyFuncListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(MyFuncParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(MyFuncParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#fun}.
	 * @param ctx the parse tree
	 */
	void enterFun(MyFuncParser.FunContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#fun}.
	 * @param ctx the parse tree
	 */
	void exitFun(MyFuncParser.FunContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#constr}.
	 * @param ctx the parse tree
	 */
	void enterConstr(MyFuncParser.ConstrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#constr}.
	 * @param ctx the parse tree
	 */
	void exitConstr(MyFuncParser.ConstrContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#vars}.
	 * @param ctx the parse tree
	 */
	void enterVars(MyFuncParser.VarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#vars}.
	 * @param ctx the parse tree
	 */
	void exitVars(MyFuncParser.VarsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statE}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStatE(MyFuncParser.StatEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statE}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStatE(MyFuncParser.StatEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statIf}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStatIf(MyFuncParser.StatIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statIf}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStatIf(MyFuncParser.StatIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#e}.
	 * @param ctx the parse tree
	 */
	void enterE(MyFuncParser.EContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#e}.
	 * @param ctx the parse tree
	 */
	void exitE(MyFuncParser.EContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#funcInvocation}.
	 * @param ctx the parse tree
	 */
	void enterFuncInvocation(MyFuncParser.FuncInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#funcInvocation}.
	 * @param ctx the parse tree
	 */
	void exitFuncInvocation(MyFuncParser.FuncInvocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprId}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprId(MyFuncParser.BoolExprIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprId}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprId(MyFuncParser.BoolExprIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprExprEq(MyFuncParser.BoolExprExprEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprExprEq(MyFuncParser.BoolExprExprEqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprBoolExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprBoolExprEq(MyFuncParser.BoolExprBoolExprEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprBoolExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprBoolExprEq(MyFuncParser.BoolExprBoolExprEqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprNot}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprNot(MyFuncParser.BoolExprNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprNot}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprNot(MyFuncParser.BoolExprNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprFunc}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprFunc(MyFuncParser.BoolExprFuncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprFunc}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprFunc(MyFuncParser.BoolExprFuncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprBool}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprBool(MyFuncParser.BoolExprBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprBool}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprBool(MyFuncParser.BoolExprBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprParens}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprParens(MyFuncParser.BoolExprParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprParens}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprParens(MyFuncParser.BoolExprParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExprOp}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExprOp(MyFuncParser.BoolExprOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExprOp}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExprOp(MyFuncParser.BoolExprOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyFuncParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(MyFuncParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyFuncParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(MyFuncParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprInt}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprInt(MyFuncParser.ExprIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprInt}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprInt(MyFuncParser.ExprIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprFunc}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprFunc(MyFuncParser.ExprFuncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprFunc}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprFunc(MyFuncParser.ExprFuncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprId(MyFuncParser.ExprIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprId(MyFuncParser.ExprIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprParens}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprParens(MyFuncParser.ExprParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprParens}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprParens(MyFuncParser.ExprParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprOp}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprOp(MyFuncParser.ExprOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprOp}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprOp(MyFuncParser.ExprOpContext ctx);
}