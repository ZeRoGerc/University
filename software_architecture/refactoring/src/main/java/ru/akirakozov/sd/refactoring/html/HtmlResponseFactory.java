package ru.akirakozov.sd.refactoring.html;

import com.sun.istack.internal.NotNull;

public class HtmlResponseFactory {

    @NotNull
    public HtmlResponse.Builder createResponseBuilder() {
        return new HtmlResponse.Builder();
    }
}
