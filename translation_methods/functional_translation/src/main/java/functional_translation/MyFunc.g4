grammar MyFunc ;
import LexerRules ;

prog : fun* ;

fun : ID '::' TYPE ('->' TYPE)* ';' constr+ ;

constr : ID vars '=' stat ';';

vars : (ID | INT | BOOL)* ;

stat : e # statE
    | 'if' boolExpr '{' stat '}' 'else' '{' stat '}' # statIf
    ;

e : boolExpr
    | unaryExpr
    ;

funcInvocation : '(' ID e* ')';

boolExpr : boolExpr op=(AND | OR) boolExpr # boolExprOp
    | ID # boolExprId
    | BOOL # boolExprBool
    | NOT boolExpr # boolExprNot
    | unaryExpr op=(EQ | NE | GT | GE | LT | LE) unaryExpr # boolExprExprEq
    | boolExpr op=(EQ | NE) boolExpr # boolExprBoolExprEq
    | funcInvocation # boolExprFunc
    | '(' boolExpr ')' # boolExprParens
    ;

unaryExpr:
    '-'? expr
    ;

expr: expr op=(ADD | SUB | MUL | DIV | MOD) expr # exprOp
    | ID # exprId
    | INT # exprInt
    | funcInvocation # exprFunc
    | '(' expr ')' # exprParens
    ;