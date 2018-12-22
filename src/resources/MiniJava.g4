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
    # nestedStat
    |   'if' '(' expression ')'
            statement
        'else'
            statement
    # ifElseStat
    |   'while' '(' expression ')'
            statement
    # whileStat
    |   'System.out.println' '(' expression ')' ';'
    # printStat
    |   Identifier '=' expression ';'
    # assignStat
    |   Identifier '[' expression ']' '=' expression ';'
    # arrayAssignStat
    ;

expression
    :   expression '+'  expression
    # addExp
    |   expression '-'  expression
    # subExp
    |   expression '*'  expression
    # mulExp
    |   expression '<'  expression
    # ltExp 
    |   expression '&&' expression
    # andExp
    |   expression '[' expression ']'
    # arrayAccessExp
    |   expression '.' 'length'
    # arrayLengthExp
    |   expression '.' Identifier '(' (expression (',' expression)*)? ')'
    # methodCallExp
    |   INT
    # intLitExp
    |   BOOL
    # booleanLitExp
    |   Identifier
    # identifierExp
    |   'this'
    # thisExp
    |   '-' expression
    # negExp
    |   '!' expression
    # notExp
    |   '(' expression ')'
    # parenExp
    |   'new' 'int' '[' expression ']'
    # arrayInstantiationExp
    |   'new' Identifier '(' ')'
    # objectInstantiationExp
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