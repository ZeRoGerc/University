// Generated from /Users/zerogerc/Documents/University/repository/University/translation_methods/functional_translation/src/main/java/functional_translation/MyFunc.g4 by ANTLR 4.7
package functional_translation;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyFuncParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyFuncVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(MyFuncParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#fun}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun(MyFuncParser.FunContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#constr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstr(MyFuncParser.ConstrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#vars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars(MyFuncParser.VarsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statE}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatE(MyFuncParser.StatEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statIf}
	 * labeled alternative in {@link MyFuncParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatIf(MyFuncParser.StatIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE(MyFuncParser.EContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#funcInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncInvocation(MyFuncParser.FuncInvocationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprId}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprId(MyFuncParser.BoolExprIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprExprEq(MyFuncParser.BoolExprExprEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprBoolExprEq}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprBoolExprEq(MyFuncParser.BoolExprBoolExprEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprNot}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprNot(MyFuncParser.BoolExprNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprFunc}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprFunc(MyFuncParser.BoolExprFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprBool}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprBool(MyFuncParser.BoolExprBoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprParens}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprParens(MyFuncParser.BoolExprParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprOp}
	 * labeled alternative in {@link MyFuncParser#boolExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprOp(MyFuncParser.BoolExprOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyFuncParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(MyFuncParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprInt}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprInt(MyFuncParser.ExprIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprFunc}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFunc(MyFuncParser.ExprFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprId(MyFuncParser.ExprIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprParens}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprParens(MyFuncParser.ExprParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprOp}
	 * labeled alternative in {@link MyFuncParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOp(MyFuncParser.ExprOpContext ctx);
}