package api;

import api.parse.DogApiParser;
import api.query.SearchQuery;
import com.sun.istack.internal.NotNull;
import network.NetworkClient;
import org.json.simple.parser.ParseException;

import java.util.List;

public class DogApiClientImpl implements DogApiClient {

    @NotNull
    private final NetworkClient networkClient;

    @NotNull
    private final DogApiParser apiParser;

    public DogApiClientImpl(@NotNull NetworkClient networkClient, @NotNull DogApiParser apiParser) {
        this.networkClient = networkClient;
        this.apiParser = apiParser;
    }

    @Override
    public int getNumberOfDogPicturesByQuery(@NotNull SearchQuery query) throws ParseException {
        final String json = networkClient.readAsText(query.getQuery());
        List<String> pictures = apiParser.getImagesFromJson(json);
        return pictures.size();
    }
}
