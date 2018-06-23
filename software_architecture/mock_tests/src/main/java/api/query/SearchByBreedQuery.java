package api.query;

import org.jetbrains.annotations.NotNull;

public class SearchByBreedQuery implements SearchQuery {

    @NotNull
    private final String breedName;

    public SearchByBreedQuery(@NotNull String breedName) {
        this.breedName = breedName;
    }

    @Override
    public String getQuery() {
        return String.format("%s/breed/%s/images", API_PREFIX, breedName);
    }
}
