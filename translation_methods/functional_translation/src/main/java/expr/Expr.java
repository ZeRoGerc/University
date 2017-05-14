package expr;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

public class Expr {

    public static void main(String[] args) throws Exception {
        final InputStream input;
        if (args.length > 0) {
            input = new FileInputStream(args[0]);
        } else {
            input = System.in;
        }

        ExprLexer lexer = new ExprLexer(CharStreams.fromStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();

        EvalVisitor visitor = new EvalVisitor();

        visitor.visit(tree);
    }
}
