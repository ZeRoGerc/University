package java_translation;

import expr.EvalVisitor;
import expr.ExprLexer;
import expr.ExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStream;

public class Java {

    public static void main(String[] args) throws Exception {
        final InputStream input;
        if (args.length > 0) {
            input = new FileInputStream(args[0]);
        } else {
            input = System.in;
        }

        JavaLexer lexer = new JavaLexer(CharStreams.fromStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExtractInterfaceListener extractor = new ExtractInterfaceListener(parser);

        try {
            walker.walk(extractor, tree);
        } finally {
            extractor.closeFile();
        }
    }
}
