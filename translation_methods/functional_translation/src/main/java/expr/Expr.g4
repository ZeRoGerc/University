grammar Expr;
import CommonLexerRules;

prog: stat+ ;

stat: expr NEWLINE # printExpr
    | ID '=' expr NEWLINE # assign
    | CLEAR NEWLINE # clear
    | NEWLINE # blank
    ;

expr: expr op=(MUL | DIV) expr # MulDiv
    | expr op=(ADD | SUB) expr # AddSub
    | INT # int
    | ID # id
    | '(' expr ')' # parens
    ;

MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
CLEAR : 'clear' ;