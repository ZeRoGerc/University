package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ProductDatabase productDatabase = ProductDatabase.getInstance("jdbc:sqlite:prod.db");
        HtmlResponseFactory responseFactory = new HtmlResponseFactory();

        productDatabase.clear();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDatabase, responseFactory)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDatabase, responseFactory)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDatabase, responseFactory)),"/query");

        server.start();
        server.join();
    }
}
