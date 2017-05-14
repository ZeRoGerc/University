package java_translation;

import org.antlr.v4.runtime.TokenStream;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ExtractInterfaceListener extends JavaBaseListener {

    private JavaParser parser;

    private PrintWriter printer;

    public ExtractInterfaceListener(JavaParser parser) throws Exception {
        this.parser = parser;
        FileWriter fileWriter = new FileWriter("src/main/resources/out.txt");
        printer = new PrintWriter(fileWriter);
    }

    public void closeFile() {
        printer.close();
    }

    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        printer.append(parser.getTokenStream().getText(ctx) + "\n");
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        printer.append(parser.getTokenStream().getText(ctx) + "\n");
    }

    /**
     * Listen to matches of classDeclaration
     */
    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        printer.append("interface I" + ctx.Identifier() + " {\n");
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        printer.append("}\n");
    }

    /**
     * Listen to matches of methodDeclaration
     */
    @Override
    public void enterMethodDeclaration(
            JavaParser.MethodDeclarationContext ctx
    ) {
        // need parser to get tokens
        TokenStream tokens = parser.getTokenStream();
        String type = "void";
        if (ctx.type() != null) {
            type = tokens.getText(ctx.type());
        }
        String args = tokens.getText(ctx.formalParameters());
        printer.append("\t" + type + " " + ctx.Identifier() + args + ";\n");
    }
}
