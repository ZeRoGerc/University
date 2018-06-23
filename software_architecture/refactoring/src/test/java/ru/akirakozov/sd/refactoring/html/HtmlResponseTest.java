package ru.akirakozov.sd.refactoring.html;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class HtmlResponseTest {

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    PrintWriter printWriter;

    HtmlResponse htmlResponse = new HtmlResponse("response");

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        doReturn(printWriter).when(httpServletResponse).getWriter();
    }

    @Test
    public void makeOkResponse_shouldCallProperMethods() throws Exception {
        htmlResponse.makeOkResponse(httpServletResponse);

        verify(httpServletResponse).setContentType("text/html");
        verify(httpServletResponse).setStatus(HttpServletResponse.SC_OK);
        verify(httpServletResponse).getWriter();
        verify(printWriter).println("response");
    }
}