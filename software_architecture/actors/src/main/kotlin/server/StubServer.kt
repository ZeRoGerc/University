package server

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class StubServer {

    class YandexSearchServlet : HttpServlet() {
        override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
                doGetWithPrefix("yandex", request, response)
    }

    class GoogleSearchServlet : HttpServlet() {
        override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
                doGetWithPrefix("google", request, response)
    }

    class BingSearchServlet : HttpServlet() {
        override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
                doGetWithPrefix("bing", request, response)
    }

    companion object {
        fun doGetWithPrefix(
                prefix: String,
                request: HttpServletRequest,
                response: HttpServletResponse
        ) {
            val query = request.getParameter("query")

            response.contentType = "text/json"
            response.status = HttpServletResponse.SC_OK
            response.writer.println(createStubResponse(prefix, query))
        }

        private fun createStubResponse(prefix: String, query: String): String {
            var top = ""
            for (i in 0..4) {
                top += """
                    {
                        "title": "${prefix}_title_$i",
                        "body": "$query$i"
                    },
                    """
            }
            return """
                {
                "top" : [
                    $top
                ]
                }
                 """
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // test: http://localhost:8081/google?query=cats
            val server = Server(8081)

            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            context.contextPath = "/"
            server.handler = context

            context.addServlet(ServletHolder(YandexSearchServlet()), "/yandex")
            context.addServlet(ServletHolder(GoogleSearchServlet()), "/google")
            context.addServlet(ServletHolder(BingSearchServlet()), "/bing")

            server.start()
            server.join()
        }
    }
}