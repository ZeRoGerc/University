lexer grammar LexerRules;

MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
MOD : '%' ;

AND : '&&' ;
OR : '||' ;
NOT : '!' ;

EQ : '==' ;
NE : '!=' ;
GT : '>' ;
GE : '>=' ;
LT : '<' ;
LE : '<=' ;

TYPE : 'Int' | 'Bool';
BOOL : 'True' | 'False';
ID : [a-zA-Z][a-zA-Z0-9]* ;
INT : [+-]?[0-9]+ ;
WS : [ \t\r\n]+ -> skip ;