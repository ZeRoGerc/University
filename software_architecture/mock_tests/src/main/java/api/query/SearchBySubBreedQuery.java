package api.query;

import org.jetbrains.annotations.NotNull;

public class SearchBySubBreedQuery implements SearchQuery {

    @NotNull
    private final String breedName;

    @NotNull
    private final String subBreedName;

    public SearchBySubBreedQuery(@NotNull String breedName, @NotNull String subBreedName) {
        this.breedName = breedName;
        this.subBreedName = subBreedName;
    }

    @Override
    public String getQuery() {
        return String.format("%s/breed/%s/%s/images", API_PREFIX, breedName, subBreedName);
    }
}
