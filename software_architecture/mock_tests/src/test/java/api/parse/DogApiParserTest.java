package api.parse;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DogApiParserTest {

    DogApiParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new DogApiParserImpl();
    }

    @Test
    public void getImagesFromJson() throws Exception {
        List<String> images = parser.getImagesFromJson(
                "{" +
                        "\"status\":\"success\"," +
                        "\"message\":[\"imageurl1\", \"imageurl2\"] " +
                "}"
        );
        assertThat(images.toArray(new String[0])).containsExactly("imageurl1", "imageurl2");
    }
}
