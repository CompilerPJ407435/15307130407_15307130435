# MiniJava编译器前端实现
## 概述
通过利用语言识别工具Antlr和Java构建构造一个编译器的前端，将输入的 miniJava 语言代码段进行词法分析和语法分析、转化成抽象语法树，并实现语义分析。同时对编译器进行优化，使得编译器在错误显示等方便更加用户友好。

## 组员分工
* [@15307130407](https://github.com/sweetLychee)
语义分析、语义错误、实现文件输入、调试代码、撰写报告；
* [@15307140435](https://github.com/Alaaask)
词法语法分析、输出AST、错误处理、额外功能、调试代码、撰写报告；

## 开发平台
* MacOS／Ubuntu;
* openjdk version 11.0.1

## 依赖说明
* 安装 Java
* 安装 [Antlr4.7.1](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md)

## 文件结构
**Resources目录**下为文法文件、解释器数据文件和语法间共享信息，以及tokens文件。
**MiniJava目录**下为antlr4依据文法文件实现的各Java类。
**Compiler目录**有Compiler.java，为编译器前端提供用户输入接口。
**Testfiles目录**下为测试用例，用于开发者测试与调试程序，以及向用户展示demo。

**核心代码**部分主要集中在resources目录下的文法文件MiniJava.g4以及Compiler目录下的Compiler类。

## 功能描述
1. 词法分析
2. 语法分析
3. 输出AST
4. 语义分析
5. 错误处理（提示错误类型、出错位置）
6. 额外功能

## 使用方法

若从文法文件开始生成
```
cd ./src/resources
antlr4 MiniJava.g4
```

编译 MiniJava Parser Lexer等类
```
cd ../MiniJava
javac -d ../../bin *.java
```

编译编译器前端 Compiler（本地依赖路径自行修改）
```
cd ../Compiler
javac -d ../../bin/ -cp .:../../bin:/usr/local/lib/antlr-4.7.1-complete.jar Compiler.java
```

使用此编译器前端：由命令行 java Compiler [目标文件路径] 实现对文件的编译
```
cd ../../bin
java Compiler [目标文件路径]
```