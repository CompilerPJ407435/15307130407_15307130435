### 编写文法文件
>以[BNF](http://www.cambridge.org/us/features/052182060X/grammar.html)为参考编写文法文件，以小写字母开头的是语法规则，以大写字母开头的是词法规则

### 输出AST
>从标准输入读取字符流，并调用生成的Lexer和Parser解析，用string和View两种方式方式输出AST

### 区分错误类型
>为语法错误和词法错误实现自己的提示器，并标注是语法错误还是词法错误

##额外功能
### 添加新特性
>expression支持取负号(-)

### 让错误提示更加user-friendly
>错误处理格式化，使错误位置显示更user-friendly,并通过下划线出错位置使之更醒目与了然
>对一些可能出现的错误，在文法文件中加以注明，使语法错误逻辑更清晰，如：
```
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

type   
    :   (('int' '[' ']'
    |	'boolean'
    |	'int'
    |	Identifier)
        |
        {notifyErrorListeners("invalid type");})
    ;
```

###参考文献
http://www.cambridge.org/us/features/052182060X/grammar.html
https://www.antlr.org/api/
http://media.pragprog.com/titles/tpantlr/errors.pdf
