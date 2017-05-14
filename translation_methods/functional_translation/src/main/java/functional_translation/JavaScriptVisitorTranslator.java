package functional_translation;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaScriptVisitorTranslator extends MyFuncBaseVisitor<String> {

    private MyFuncParser parser;

    private Map<String, String> renamingMap = new HashMap<>();

    private String currentFunctionName;

    private List<Type> functionTypes = new ArrayList<>();

    public JavaScriptVisitorTranslator(MyFuncParser parser) throws Exception {
        this.parser = parser;
    }

    @Override
    public String visitProg(MyFuncParser.ProgContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (MyFuncParser.FunContext child : ctx.fun()) {
            builder.append(visitFun(child));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public String visitFun(MyFuncParser.FunContext ctx) {
        assert !ctx.TYPE().isEmpty();

        StringBuilder builder = new StringBuilder();
        functionTypes.clear();
        currentFunctionName = ctx.ID().getText();

        for (TerminalNode type: ctx.TYPE()) {
            functionTypes.add(Type.fromString(type.getSymbol().getText()));
        }

        builder.append("def ");
        builder.append(ctx.ID().getSymbol().getText()).append("(");
        for (int i = 0; i < ctx.TYPE().size() - 2; i++) {
            builder.append(getVarName(i)).append(", ");
        }
        if (ctx.TYPE().size() != 1) {
            builder.append(getVarName(ctx.TYPE().size() - 2));
        }
        builder.append("):\n");

        for (MyFuncParser.ConstrContext child : ctx.constr()) {
            appendWithSpaces(builder, visitConstr(child), 4);
        }

        return builder.toString();
    }

    @Override
    public String visitConstr(MyFuncParser.ConstrContext ctx) {
        assertWithError(
                currentFunctionName.equals(ctx.ID().getText()),
                "Function name in constraint must correspond to function name in definition" +
                        "Expected: " + currentFunctionName +
                        " Got: " + ctx.ID().getText()
        );
        StringBuilder builder = new StringBuilder();
        boolean ifAppended = false;

        String constraints = visitVars(ctx.vars());
        if (!constraints.isEmpty()) {
            builder.append("if ")
                    .append(constraints)
                    .append(":\n");
        }

        appendWithSpaces(
                builder,
                visitStat(ctx.stat()),
                constraints.isEmpty() ? 0 : 4
        );

        return builder.toString();
    }

    @Override
    public String visitVars(MyFuncParser.VarsContext ctx) {
        assertWithError(
                ctx.getChildCount() == functionTypes.size() - 1,
                "Number of variables in constraint must be equal to the number of types in function declaration." +
                        "Expected: " + (functionTypes.size() - 1) + " types" +
                        " But got: \"" + ctx.getText() + "\""
        );

        StringBuilder builder = new StringBuilder();
        renamingMap.clear();

        int condNumber = 0;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            assert ctx.getChild(i) instanceof TerminalNode;

            TerminalNode node = (TerminalNode) ctx.getChild(i);

            if (node.getSymbol().getType() == MyFuncParser.INT) {
                assertWithError(
                        functionTypes.get(i) == Type.INT,
                        "Type in constraint must correspond to the type in function declaration. " +
                                "Expected : " + functionTypes.get(i).getTypeName() +
                                " Got: " + node.getSymbol().getText()
                );

                appendConstraint(builder, condNumber, getVarName(i), node.getSymbol().getText());
                condNumber++;
            } else if (node.getSymbol().getType() == MyFuncParser.BOOL) {
                assertWithError(
                        functionTypes.get(i) == Type.BOOL,
                        "Type in constraint must correspond to the type in function declaration. " +
                                "Expected : " + functionTypes.get(i).getTypeName() +
                                " Got: " + node.getSymbol().getText()
                );

                appendConstraint(builder, condNumber, getVarName(i), node.getSymbol().getText());
                condNumber++;
            } else {
                renamingMap.put(node.getSymbol().getText(), getVarName(i));
            }
        }

        String answer = builder.toString();
        if (condNumber == 1) {
            return answer.substring(1, answer.length() - 1);
        } else {
            return answer;
        }
    }

    private void appendConstraint(StringBuilder builder, int condNumber, String varName, String value) {
        if (condNumber == 0) {
            builder.append("(")
                    .append(varName)
                    .append(" == ")
                    .append(value)
                    .append(")");
        } else {
            builder.append(" && (")
                    .append(varName)
                    .append(" == ")
                    .append(value)
                    .append(")");
        }
    }

    @Override
    public String visitStatIf(MyFuncParser.StatIfContext ctx) {
        StringBuilder builder = new StringBuilder();
        builder.append("if (")
                .append(visitBoolExpr(ctx.boolExpr()))
                .append("):\n");

        appendWithSpaces(
                builder,
                visitStat(ctx.stat(0)),
                4
        );
        builder.append("else:\n");
        appendWithSpaces(
                builder,
                visitStat(ctx.stat(1)),
                4
        );

        return builder.toString();
    }

    @Override
    public String visitStatE(MyFuncParser.StatEContext ctx) {
        return "return " + visitE(ctx.e()) + "\n";
    }

    @Override
    public String visitE(MyFuncParser.EContext ctx) {
        final ParseTree tree = ctx.getChild(0);
        if (tree instanceof MyFuncParser.UnaryExprContext) {
            return visitUnaryExpr((MyFuncParser.UnaryExprContext) tree);
        } else if (tree instanceof MyFuncParser.BoolExprContext) {
            return visitBoolExpr((MyFuncParser.BoolExprContext) tree);
        } else {
            throw new IllegalStateException("Unknown ctx instance");
        }
    }

    @Override
    public String visitUnaryExpr(MyFuncParser.UnaryExprContext ctx) {
        String exprStr = visitExpr(ctx.expr());
        if (ctx.getChildCount() > 1) {
            return "-" + exprStr + "";
        }
        return exprStr;
    }

    @Override
    public String visitBoolExprOp(MyFuncParser.BoolExprOpContext ctx) {
        String op = ctx.op.getText();
        switch (op) {
            case "&&" :
                op = "and";
                break;
            case "||" :
                op = "or";
                break;
            default :
                throw new IllegalStateException("Unknown operation: " + op);
        }
        return visitBoolExpr(ctx.boolExpr(0)) + " " + op + " " + visitBoolExpr(ctx.boolExpr(1));
    }

    @Override
    public String visitBoolExprId(MyFuncParser.BoolExprIdContext ctx) {
        return extractVariable(ctx);
    }

    @Override
    public String visitBoolExprBool(MyFuncParser.BoolExprBoolContext ctx) {
        return parser.getTokenStream().getText(ctx);
    }

    @Override
    public String visitBoolExprNot(MyFuncParser.BoolExprNotContext ctx) {
        return "not (" + visitBoolExpr(ctx.boolExpr()) + ")";
    }

    @Override
    public String visitBoolExprExprEq(MyFuncParser.BoolExprExprEqContext ctx) {
        return visitUnaryExpr(ctx.unaryExpr(0)) + " " + ctx.op.getText() + " " + visitUnaryExpr(ctx.unaryExpr(1));
    }

    @Override
    public String visitBoolExprBoolExprEq(MyFuncParser.BoolExprBoolExprEqContext ctx) {
        return visitBoolExpr(ctx.boolExpr(0)) + " " + ctx.op.getText() + " " + visitBoolExpr(ctx.boolExpr(1));
    }

    @Override
    public String visitBoolExprFunc(MyFuncParser.BoolExprFuncContext ctx) {
        return visitFuncInvocation(ctx.funcInvocation());
    }

    @Override
    public String visitBoolExprParens(MyFuncParser.BoolExprParensContext ctx) {
        return "(" + visitBoolExpr(ctx.boolExpr()) + ")";
    }

    @Override
    public String visitExprOp(MyFuncParser.ExprOpContext ctx) {
        return visitExpr(ctx.expr(0)) + " " + ctx.op.getText() + " " + visitExpr(ctx.expr(1));
    }

    @Override
    public String visitExprInt(MyFuncParser.ExprIntContext ctx) {
        return parser.getTokenStream().getText(ctx);
    }

    @Override
    public String visitExprId(MyFuncParser.ExprIdContext ctx) {
        return extractVariable(ctx);
    }

    @Override
    public String visitExprFunc(MyFuncParser.ExprFuncContext ctx) {
        return visitFuncInvocation(ctx.funcInvocation());
    }

    @Override
    public String visitExprParens(MyFuncParser.ExprParensContext ctx) {
        return "(" + visitExpr(ctx.expr()) + ")";
    }

    @Override
    public String visitFuncInvocation(MyFuncParser.FuncInvocationContext ctx) {
        if (ctx.e().isEmpty() && renamingMap.containsKey(ctx.ID().getText())) { // it is a variable
            return renamingMap.get(ctx.ID().getText());
        }

        StringBuilder builder = new StringBuilder();

        builder.append(ctx.ID().getText()).append("(");

        for (MyFuncParser.EContext child : ctx.e()) {
            builder.append(visitE(child)).append(", ");
        }

        if (!ctx.e().isEmpty()) {
            builder.delete(builder.length() - 2, builder.length());
        }

        builder.append(")");

        return builder.toString();
    }

    private String extractVariable(ParserRuleContext ctx) {
        String name = parser.getTokenStream().getText(ctx);
        assert renamingMap.containsKey(name);

        return renamingMap.get(name);
    }

    private String visitStat(MyFuncParser.StatContext ctx) {
        if (ctx instanceof MyFuncParser.StatEContext) {
            return visitStatE((MyFuncParser.StatEContext) ctx);
        } else if (ctx instanceof MyFuncParser.StatIfContext) {
            return visitStatIf((MyFuncParser.StatIfContext) ctx);
        } else {
            throw new IllegalStateException("Unknown ctx instance");
        }
    }

    private String visitExpr(MyFuncParser.ExprContext ctx) {
        if (ctx instanceof MyFuncParser.ExprOpContext) {
            return visitExprOp((MyFuncParser.ExprOpContext) ctx);
        } else if (ctx instanceof MyFuncParser.ExprIntContext) {
            return visitExprInt((MyFuncParser.ExprIntContext) ctx);
        } else if (ctx instanceof MyFuncParser.ExprIdContext) {
            return visitExprId((MyFuncParser.ExprIdContext) ctx);
        } else if (ctx instanceof MyFuncParser.ExprFuncContext) {
            return visitExprFunc((MyFuncParser.ExprFuncContext) ctx);
        } else if (ctx instanceof MyFuncParser.ExprParensContext) {
            return visitExprParens((MyFuncParser.ExprParensContext) ctx);
        } else {
            throw new IllegalStateException("Unknown ctx instance");
        }
    }

    private String visitBoolExpr(MyFuncParser.BoolExprContext ctx) {
        if (ctx instanceof MyFuncParser.BoolExprOpContext) {
            return visitBoolExprOp((MyFuncParser.BoolExprOpContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprIdContext) {
            return visitBoolExprId((MyFuncParser.BoolExprIdContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprBoolContext) {
            return visitBoolExprBool((MyFuncParser.BoolExprBoolContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprNotContext) {
            return visitBoolExprNot((MyFuncParser.BoolExprNotContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprExprEqContext) {
            return visitBoolExprExprEq((MyFuncParser.BoolExprExprEqContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprBoolExprEqContext) {
            return visitBoolExprBoolExprEq((MyFuncParser.BoolExprBoolExprEqContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprFuncContext) {
            return visitBoolExprFunc((MyFuncParser.BoolExprFuncContext) ctx);
        } else if (ctx instanceof MyFuncParser.BoolExprParensContext) {
            return visitBoolExprParens((MyFuncParser.BoolExprParensContext) ctx);
        } else {
            throw new IllegalStateException("Unknown ctx instance");
        }
    }

    private String getVarName(int num) {
        return "x" + (num + 1);
    }

    private void appendWithSpaces(StringBuilder builder, String toAppend, int spaces) {
        String[] lines = toAppend.split("\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < spaces; j++) {
                builder.append(" ");
            }

            builder.append(lines[i]);
            if (toAppend.charAt(toAppend.length() - 1) == '\n' || i != lines.length - 1) {
                builder.append("\n");
            }
        }
    }

    private void assertWithError(boolean value, String message) {
        if (!value) {
            throw new IllegalStateException(message);
        }
    }
}
