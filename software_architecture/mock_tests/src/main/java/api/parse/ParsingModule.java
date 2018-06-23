package api.parse;

import org.jetbrains.annotations.NotNull;

public class ParsingModule {

    @NotNull
    private final DogApiParser dogApiParser;

    public ParsingModule() {
        this.dogApiParser = new DogApiParserImpl();
    }

    @NotNull
    public DogApiParser getDogApiParser() {
        return dogApiParser;
    }
}
