package api.parse;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface DogApiParser {

    /**
     * Parse list of images from json returned by search queries.
     */
    @NotNull
    List<String> getImagesFromJson(@NotNull String json) throws ParseException;
}
