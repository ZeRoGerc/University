package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.db.Product;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponse;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GetProductsServletTest {

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

    GetProductsServlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet(productDatabase, responseFactory);

        doReturn(htmlResponseBuilder).when(responseFactory).createResponseBuilder();
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addLine(anyString());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addProduct(any());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addOptionalProduct(anyString(), any());
        doReturn(htmlResponse).when(htmlResponseBuilder).build();
    }

    @Test
    public void doGet_shouldWork() throws Exception {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        doReturn(products).when(productDatabase).getProducts();

        servlet.doGet(request, response);

        verify(productDatabase).getProducts();
        verify(htmlResponseBuilder).addProduct(product1);
        verify(htmlResponseBuilder).addProduct(product2);
        verify(htmlResponse).makeOkResponse(response);
    }
}
