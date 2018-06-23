package ru.akirakozov.sd.refactoring.servlet;

import com.sun.istack.internal.NotNull;
import ru.akirakozov.sd.refactoring.db.Product;
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
public class GetProductsServlet extends HttpServlet {

    @NotNull
    private final ProductDatabase productDatabase;

    @NotNull
    private final HtmlResponseFactory responseFactory;

    public GetProductsServlet(@NotNull ProductDatabase productDatabase, @NotNull HtmlResponseFactory responseFactory) {
        this.productDatabase = productDatabase;
        this.responseFactory = responseFactory;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HtmlResponse.Builder builder = responseFactory.createResponseBuilder();

        for (Product product : productDatabase.getProducts()) {
            builder.addProduct(product);
        }
        builder.build().makeOkResponse(response);
    }
}
