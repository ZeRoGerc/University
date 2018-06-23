import api.ApiModule;
import api.DogApiClient;
import api.parse.ParsingModule;
import api.query.SearchByBreedQuery;
import api.query.SearchBySubBreedQuery;
import network.NetworkModule;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(@NotNull String[] args) throws ParseException {
        NetworkModule networkModule = new NetworkModule();
        ParsingModule parsingModule = new ParsingModule();
        ApiModule apiModule = new ApiModule(networkModule, parsingModule);

        DogApiClient client = apiModule.getDogApiClient();

        System.out.println(client.getNumberOfDogPicturesByQuery(
                new SearchByBreedQuery("hound"))
        );
        System.out.println(client.getNumberOfDogPicturesByQuery(
                new SearchBySubBreedQuery("hound", "afghan"))
        );
    }
}
