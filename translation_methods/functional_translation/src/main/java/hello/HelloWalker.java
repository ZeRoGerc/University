package hello;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWalker extends HelloBaseListener {

    private PrintWriter printer;

    public HelloWalker() throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/out.txt");
        printer = new PrintWriter(fileWriter);
    }

    public void closeFile() {
        printer.close();
    }

    public void enterR(HelloParser.RContext ctx) {
        printer.append("Entering R : " + ctx.name().getText() + "\n");
    }

    public void exitR(HelloParser.RContext ctx) {
        printer.append("Exiting R" + "\n");
    }

    @Override
    public void enterName(HelloParser.NameContext ctx) {
        printer.append("Entering NAME : " + ctx.getText() + "\n");
    }

    @Override
    public void exitName(HelloParser.NameContext ctx) {
        printer.append("Exiting NAME" + "\n");
    }
}
