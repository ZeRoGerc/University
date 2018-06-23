package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.db.Product;
import ru.akirakozov.sd.refactoring.db.ProductDatabase;
import ru.akirakozov.sd.refactoring.html.HtmlResponse;
import ru.akirakozov.sd.refactoring.html.HtmlResponseFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class AddProductServletTest {

    @Captor
    ArgumentCaptor<Product> captor;

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

    AddProductServlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new AddProductServlet(productDatabase, responseFactory);

        doReturn(htmlResponseBuilder).when(responseFactory).createResponseBuilder();
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addLine(anyString());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addProduct(any());
        doReturn(htmlResponseBuilder).when(htmlResponseBuilder).addOptionalProduct(anyString(), any());
        doReturn(htmlResponse).when(htmlResponseBuilder).build();
    }

    @Test
    public void doGet_shouldWork() throws Exception {
        doReturn("name").when(request).getParameter(eq("name"));
        doReturn("1").when(request).getParameter(eq("price"));

        servlet.doGet(request, response);

        verify(productDatabase).addProduct(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(new Product("name", 1));

        verify(htmlResponseBuilder).addLine("OK");
        verify(htmlResponse).makeOkResponse(response);
    }
}
