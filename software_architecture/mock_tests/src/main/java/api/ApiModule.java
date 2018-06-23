package api;

import api.parse.ParsingModule;
import com.sun.istack.internal.NotNull;
import network.NetworkModule;

public class ApiModule {

    @NotNull
    private final NetworkModule networkModule;

    @NotNull
    private final ParsingModule parsingModule;

    @NotNull
    private final DogApiClient dogApiClient;

    public ApiModule(@NotNull NetworkModule networkModule, @NotNull ParsingModule parsingModule) {
        this.networkModule = networkModule;
        this.parsingModule = parsingModule;
        this.dogApiClient = new DogApiClientImpl(networkModule.getNetworkClient(), parsingModule.getDogApiParser());
    }

    @NotNull
    public DogApiClient getDogApiClient() {
        return this.dogApiClient;
    }
}
