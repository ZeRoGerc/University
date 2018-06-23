package network;

import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NetworkClientStubTest {

    private static final int PORT = 8008;

    private NetworkClient client;

    @Before
    public void setUp() throws Exception {
        client = new NetworkClientImpl();
    }

    @Test
    public void readAsText() throws Exception {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(Condition.startsWithUri("/ping"))
                    .then(stringContent("pong"));

            String result = client.readAsText("http://localhost:" + PORT + "/ping");
            assertThat(result).isEqualTo("pong\n");
        });
    }

    @Test(expected = UncheckedIOException.class)
    public void readAsString_shouldThrowIfNotFound() throws Exception {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(Condition.startsWithUri("/ping"))
                    .then(status(HttpStatus.NOT_FOUND_404));

            String result = client.readAsText("http://localhost:" + PORT + "/ping");
            assertThat(result).isEqualTo("pong\n");
        });
    }

    private void withStubServer(int port, @NotNull Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
