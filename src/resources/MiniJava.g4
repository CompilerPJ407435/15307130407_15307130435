grammar MiniJava;

@header {
package MiniJava;
}

goal
    :   mainClass
        classDeclaration*
        EOF
    ;

mainClass
    :   'class' Identifier
        mainClassBody
    ;

mainClassBody
    :   '{' mainMethodDeclaration
        '{' statement '}'
        '}'
    ;

mainMethodDeclaration
    :   'public' 'static' 'void' 'main' 
        '(' 'String' '[' ']' Identifier ')'
    ;

classDeclaration
    :   'class' Identifier
        ('extends' Identifier)?
        classBody
    ;

classBody
    :   '{'
        varDeclaration* 
        methodDeclaration*
        '}'
    ;

varDeclaration
    :   type Identifier ';'
    ;

methodDeclaration
    :   'public' type Identifier paramList
        methodBody
    ;

methodBody
    :   '{'
        varDeclaration* 
        statement*
        'return' expression ';'
        '}'
    ;

paramList
    :   '(' (type Identifier (',' type Identifier)*)? ')'
    ;

type   
    :   'int' '[' ']'
    |	'boolean'
    |	'int'
    |	Identifier
    ;

statement
    :   '{' statement* '}'
    |   'if' '(' expression ')'
            statement
        'else'
            statement
    |   'while' '(' expression ')'
            statement
    |   'System.out.println' '(' expression ')' ';'
    |   Identifier '=' expression ';'
    |   Identifier '[' expression ']' '=' expression ';'
    ;

expression
    :   expression '+'  expression
    |   expression '-'  expression
    |   expression '*'  expression
    |   expression '<'  expression
    |   expression '&&' expression
    |   expression '[' expression ']'
    |   expression '.' 'length'
    |   expression '.' Identifier '(' (expression (',' expression)*)? ')'
    |   INT
    |   BOOL
    |   Identifier
    |   'this'
    |   '-' expression
    |   '!' expression
    |   '(' expression ')'
    |   'new' 'int' '[' expression ']'
    |   'new' Identifier '(' ')'
    ;

INT
    :   ('0' | [1-9][0-9]*) 
    ;

BOOL
    :   'true'
    |   'false'
    ;

Identifier
    :   [a-zA-Z_][0-9a-zA-Z_]*
    ;

WS
    :   [ \r\t\n]+ -> skip
    ;

COMMENT
    : '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;