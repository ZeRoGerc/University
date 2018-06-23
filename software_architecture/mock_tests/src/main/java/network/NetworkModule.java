package network;

import org.jetbrains.annotations.NotNull;

public class NetworkModule {

    @NotNull
    private final NetworkClient networkClient;

    public NetworkModule() {
        this.networkClient = new NetworkClientImpl();
    }

    @NotNull
    public NetworkClient getNetworkClient() {
        return this.networkClient;
    }
}
