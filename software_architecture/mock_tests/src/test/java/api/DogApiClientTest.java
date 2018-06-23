package api;

import api.parse.DogApiParser;
import api.query.SearchQuery;
import network.NetworkClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class DogApiClientTest {

    @Mock
    NetworkClient networkClient;

    @Mock
    DogApiParser parser;

    DogApiClient apiClient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        apiClient = new DogApiClientImpl(networkClient, parser);
    }

    @Test
    public void getNumberOfDogsByQuery_shouldReturnNumberOfDogs() throws Exception {
        Mockito.doReturn("response").when(networkClient).readAsText("/query");
        Mockito.doReturn(Arrays.asList("url1", "url2")).when(parser).getImagesFromJson("response");
        SearchQuery query = () -> "/query";

        Assertions.assertThat(apiClient.getNumberOfDogPicturesByQuery(query)).isEqualTo(2);
    }
}
