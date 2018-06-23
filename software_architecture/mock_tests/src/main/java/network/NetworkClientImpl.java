package network;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkClientImpl implements NetworkClient {

    @Override
    @NotNull
    public String readAsText(@NotNull String sourceUrl) {
        URL url = toUrl(sourceUrl);
        try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder builder = new StringBuilder();
            String inputLine;

            while ((inputLine=in.readLine()) != null) {
                builder.append(inputLine);
                builder.append("\n");
            }

            return builder.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @NotNull
    private URL toUrl(@NotNull String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: " + stringUrl);
        }
    }

}
