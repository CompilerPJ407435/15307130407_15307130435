import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.regex.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.TreeViewer;

import MiniJava.*;


public class Compiler{
    // 自定错误提示
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


    public static void main(String[] args) throws Exception {

        // 输入
        ANTLRInputStream input = new ANTLRInputStream(System.in);

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