package ru.akirakozov.sd.refactoring.html;

import com.sun.istack.internal.NotNull;
import ru.akirakozov.sd.refactoring.db.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class HtmlResponse {

    @NotNull
    private final String response;

    HtmlResponse(@NotNull String response) {
        this.response = response;
    }

    public void makeOkResponse(@NotNull HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().println(response);
    }

    public static class Builder {

        @NotNull
        final StringBuilder response = new StringBuilder();

        Builder() {
            response.append("</body></html>");
        }

        @NotNull
        public HtmlResponse build() {
            response.append("</body></html>");
            return new HtmlResponse(response.toString());
        }

        @NotNull
        public Builder addLine(@NotNull String line) {
            response.append(line).append("<br>");
            return this;
        }

        @NotNull
        public Builder addProduct(@NotNull Product product) {
            response.append(product.getName()).append("\t").append(product.getPrice()).append("<br>");
            return this;
        }

        @NotNull
        public Builder addOptionalProduct(@NotNull String description, @NotNull Optional<Product> product) {
            if (product.isPresent()) {
                this.addLine(description + ": ");
                this.addProduct(product.get());
            } else {
                this.addLine("No products in database");
            }
            return this;
        }
    }
}
