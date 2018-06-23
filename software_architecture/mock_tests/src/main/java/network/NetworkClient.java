package network;

import org.jetbrains.annotations.NotNull;

public interface NetworkClient {

    @NotNull
    String readAsText(@NotNull String sourceUrl);
}
