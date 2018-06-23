package ru.akirakozov.sd.refactoring.servlet;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.*;
import ru.akirakozov.sd.refactoring.db.Product;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponse;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class QueryServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HtmlResponse.Builder htmlResponseBuilder;

    @Mock
    HtmlResponse htmlResponse;

    @Mock
    ProductDatabase productDatabase;

    @Mock
    HtmlResponseFactory responseFactory;

    @Mock
    Product product;

    QueryServlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new QueryServlet(productDatabase, responseFactory);

        doReturn(htmlResponseBuilder).when(responseFactory).createResponseBuilder();
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addLine(anyString());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addProduct(any());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addOptionalProduct(anyString(), any());
        doReturn(htmlResponse).when(htmlResponseBuilder).build();
    }

    @Test
    public void doGet_shouldWorkOnMax() throws Exception {
        Optional<Product> optionalProduct = Optional.of(product);

        doReturn("max").when(request).getParameter(eq("command"));
        doReturn(optionalProduct).when(productDatabase).getMax();

        servlet.doGet(request, response);

        verify(productDatabase).getMax();
        verify(htmlResponseBuilder).addOptionalProduct("Product with max price", optionalProduct);
        verify(htmlResponse).makeOkResponse(response);
    }

    @Test
    public void doGet_shouldWorkOnMin() throws Exception {
        Optional<Product> optionalProduct = Optional.of(product);

        doReturn("min").when(request).getParameter(eq("command"));
        doReturn(optionalProduct).when(productDatabase).getMin();

        servlet.doGet(request, response);

        verify(productDatabase).getMin();
        verify(htmlResponseBuilder).addOptionalProduct("Product with min price", optionalProduct);
        verify(htmlResponse).makeOkResponse(response);
    }

    @Test
    public void doGet_shouldWorkOnSum() throws Exception {
        doReturn("sum").when(request).getParameter(eq("command"));
        doReturn(100).when(productDatabase).getSum();

        servlet.doGet(request, response);

        verify(productDatabase).getSum();
        verify(htmlResponseBuilder).addLine("Summary price: 100");
        verify(htmlResponse).makeOkResponse(response);
    }

    @Test
    public void doGet_shouldWorkOnCount() throws Exception {
        doReturn("count").when(request).getParameter(eq("command"));
        doReturn(10).when(productDatabase).getCount();

        servlet.doGet(request, response);

        verify(productDatabase).getCount();
        verify(htmlResponseBuilder).addLine("Number of products: 10");
        verify(htmlResponse).makeOkResponse(response);
    }

    @Test
    public void doGet_shouldReturnUnknownCommandOnUnknownCommand() throws Exception {
        doReturn("count_number_of_cheap").when(request).getParameter(eq("command"));

        servlet.doGet(request, response);

        verify(htmlResponseBuilder).addLine("Unknown command: count_number_of_cheap");
        verify(htmlResponse).makeOkResponse(response);
    }
}
