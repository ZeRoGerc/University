package api.parse;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
import java.util.List;

public class DogApiParserImpl implements DogApiParser {

    @NotNull
    @Override
    public List<String> getImagesFromJson(@NotNull String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(json);
        JSONArray message = ((JSONArray) object.get("message"));

        @SuppressWarnings("unchecked")
        Iterator<String> it = (Iterator<String>) message.iterator();
        return Lists.newArrayList(it);
    }
}
