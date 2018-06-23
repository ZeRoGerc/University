package network;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JUnit4.class)
//@HostReachableRule.HostReachable(NetworkClientIntegrationTest.HOST)
public class NetworkClientIntegrationTest {

    public static final String HOST = "dog.ceo";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    NetworkClient client;

    @Before
    public void setUp() throws Exception {
        client = new NetworkClientImpl();
    }

    @Test
    public void readAsString_shouldWork() throws Exception {
        String response = client.readAsText("https://dog.ceo/api/breeds/list/all");
        assertThat(response).contains("labrador");
    }
}
