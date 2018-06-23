package ru.akirakozov.sd.refactoring.servlet;

import com.sun.istack.internal.NotNull;
import ru.akirakozov.sd.refactoring.db.Product;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    @NotNull
    private final ProductDatabase productDatabase;

    @NotNull
    private final HtmlResponseFactory responseFactory;

    public AddProductServlet(@NotNull ProductDatabase productDatabase, @NotNull HtmlResponseFactory responseFactory) {
        this.productDatabase = productDatabase;
        this.responseFactory = responseFactory;
    }

    @Override
    protected void doGet(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response
    ) throws IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        productDatabase.addProduct(new Product(name, price));

        responseFactory.createResponseBuilder().addLine("OK").build().makeOkResponse(response);
    }
}
