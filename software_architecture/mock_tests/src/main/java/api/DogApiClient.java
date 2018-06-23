package api;

import api.query.SearchQuery;
import com.sun.istack.internal.NotNull;
import org.json.simple.parser.ParseException;

public interface DogApiClient {

    /**
     * Perform search through dogs api and return number of matched dogs.
     */
    int getNumberOfDogPicturesByQuery(@NotNull SearchQuery query) throws ParseException;
}
