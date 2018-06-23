package ru.akirakozov.sd.refactoring.servlet;

import com.sun.istack.internal.NotNull;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponse;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @NotNull
    private final ProductDatabase productDatabase;

    @NotNull
    private final HtmlResponseFactory responseFactory;

    public QueryServlet(@NotNull ProductDatabase productDatabase, @NotNull HtmlResponseFactory responseFactory) {
        this.productDatabase = productDatabase;
        this.responseFactory = responseFactory;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        HtmlResponse.Builder builder = responseFactory.createResponseBuilder();

        switch (command) {
            case "max":
                builder.addOptionalProduct("Product with max price", productDatabase.getMax());
                break;
            case "min":
                builder.addOptionalProduct("Product with min price", productDatabase.getMin());
                break;
            case "sum":
                builder.addLine("Summary price: " + productDatabase.getSum());
                break;
            case "count":
                builder.addLine("Number of products: " + productDatabase.getCount());
                break;
            default:
                builder.addLine("Unknown command: " + command);
        }

        builder.build().makeOkResponse(response);
    }
}
