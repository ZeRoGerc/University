package functional_translation;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        final InputStream input;
        if (args.length > 0) {
            input = new FileInputStream(args[0]);
        } else {
            input = System.in;
        }

        MyFuncLexer lexer = new MyFuncLexer(CharStreams.fromStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyFuncParser parser = new MyFuncParser(tokens);
        ParseTree tree = parser.prog();

        JavaScriptVisitorTranslator visitorTranslator = new JavaScriptVisitorTranslator(parser);

        try (PrintWriter printer = new PrintWriter(new FileWriter("src/main/java/translated.py"))) {
            printer.append(visitorTranslator.visit(tree));
        }
    }
}
