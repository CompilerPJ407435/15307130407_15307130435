import java.io.*;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.regex.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.TreeViewer;

import MiniJava.*;

// MiniJava 编译器的实现
public class Compiler{
    
    // 自定错误提示
    // ParserError
    public static class MyMiniJavaParserErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e)
        {
            
            // 格式化错误处理
            System.err.println("Grammar Error!");
            System.err.println("Error Position: line " + line + ", position " + charPositionInLine);
            
            // 输出错误行并在下面标识
            underLineError(recognizer, (Token)offendingSymbol, line, charPositionInLine);
            System.err.println("Error Description: " + msg);
            
        }
        
        // 标识错误位置
        protected void underLineError(Recognizer recognizer,
                                      Token offendingToken,
                                      int line,
                                      int charPositionInLine)
        {
            // 得到输入
            CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
            String input = tokens.getTokenSource().getInputStream().toString();
            
            String[] lines = input.split("\n");
            // 找到错误行
            String errorLine = lines[line - 1];
            
            if(errorLine.charAt(0) == 0x00) {
                return;
            }
            
            System.err.println(errorLine);
            
            for(int i = 0; i < charPositionInLine; i++)
                System.err.print(" ");
            
            int start = offendingToken.getStartIndex();
            int stop = offendingToken.getStopIndex();
            if (start >= 0 && stop >= 0) {
                for(int i = start; i <= stop; i++)
                    System.err.print("^");
            }
            
            System.err.println();
        }
    }
    
    // 自定错误提示
    // LexerError
    public static class MyMiniJavaLexerErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e)
        {
            
            // 格式化错误处理
            System.err.println("Lexical Error!");
            System.err.println("Error Position: line " + line + ", position " + charPositionInLine);
            
            // 输出错误行并在下面标识
            underLineError(recognizer, offendingSymbol, line, charPositionInLine);
            System.err.println("Error Description: " + msg);
            
        }
        
        // 标识错误位置
        protected void underLineError(Recognizer recognizer,
                                      Object offendingToken,
                                      int line,
                                      int charPositionInLine)
        {
            // 得到输入
            String input = recognizer.getInputStream().toString();
            String[] lines = input.split("\n");
            
            // 找到错误行
            String errorLine = lines[line - 1];
            
            if(errorLine.charAt(0) == 0x00) {
                return;
            }
            
            System.err.println(errorLine);
            
            for(int i = 0; i < charPositionInLine; i++)
                System.err.print(" ");
            
            System.err.print("^");
            
            System.err.println();
        }
    }
    
    /** // Syntax Analysis
     private static class MyMniJavaSyntaxAnalysis{
     
     // 对语法树进行深度优先遍历，检查语义错误包括：
     // 1. 标示符未定义
     //
     public void ScopeCheck(ParseTree tree, String activeVars[]){
     // 如果没有子节点
     if(tree.getchild() == null){
     return;
     }
     String localVars[];
     for
     
     }
     }*/
    
    //inputFile: 将要进行编译分析的文件路径
    private static String inputFile = null;
    
    public static void main(String[] args) throws Exception {
        
        // 目标文件输入流
        InputStream fstream = null;
        
        // 获取目标文件的路径
        if(args.length > 0){
            System.out.println(args[0]);
            inputFile = args[0];
            fstream = new FileInputStream(inputFile);
        }
        
        ANTLRInputStream input = new ANTLRInputStream(fstream);
        
        // 进行语法分析和词法分析
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);
        
        // 移除默认的错误处理
        lexer.removeErrorListeners();
        // 添加自定义错误提示
        lexer.addErrorListener(new MyMiniJavaLexerErrorListener());
        
        // 移除默认的错误处理
        parser.removeErrorListeners();
        // 添加自定义错误提示
        parser.addErrorListener(new MyMiniJavaParserErrorListener());
        
        // 生成parse tree
        ParseTree tree = parser.goal();
        // 以字符串形式输出树
        System.out.println("---------------------Output AST--------------------");
        System.out.println("MiniJava AST in string mode:");
        System.out.println(tree.toStringTree());
        
        // 以gui形式输出树
        JFrame frame = new JFrame("MiniJava AST");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(parser.getRuleNames()),tree);
        
        viewr.setScale(1);
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setVisible(true);
    }
}
