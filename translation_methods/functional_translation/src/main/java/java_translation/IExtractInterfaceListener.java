package java_translation;

import org.antlr.v4.runtime.TokenStream;

import java.io.FileWriter;
import java.io.PrintWriter;

interface IExtractInterfaceListener {
    void closeFile();

    void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx);

    void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx);

    void enterMethodDeclaration(
            JavaParser.MethodDeclarationContext ctx
    );
}
