package api.query;

import com.sun.istack.internal.NotNull;

/**
 * Interface that represent query to twitter search method.
 */
public interface SearchQuery {

    String API_PREFIX = "https://dog.ceo/api";

    @NotNull
    String getQuery();
}
