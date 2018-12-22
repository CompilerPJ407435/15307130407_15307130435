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
    :  ('public' type Identifier paramList
        /* illegal method declarations */
        |          type Identifier paramList
            {notifyErrorListeners("method declaration without public");}
        | 'public'      Identifier paramList
            {notifyErrorListeners("method declaration without return type");}
        | 'public' type    paramList
            {notifyErrorListeners("method declaration without method name");}
        | 'public' type Identifier
            {notifyErrorListeners("method declaration without param list");}
        )
        methodBody
    ;

methodBody
    :   '{'
        varDeclaration* 
        statement*
        ('return' expression ';'
        /* illegal method without return */
        | 
        {notifyErrorListeners("method body without return");})
        '}'
    ;

paramList
    :   '(' (type Identifier (',' type Identifier)*)? ')'
    ;

type   
    :   (('int' '[' ']'
    |	'boolean'
    |	'int'
    |	Identifier)
        |
        {notifyErrorListeners("invalid type");})
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