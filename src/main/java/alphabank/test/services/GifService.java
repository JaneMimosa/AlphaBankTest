package alphabank.test.services;

import java.net.URI;

public class GifService {

    public static URI getGifURI(String gifServer, String gifApiID, String tag) {
        StringBuilder builderURL = new StringBuilder();
        builderURL
                .append(gifServer)
                .append("v1/gifs/random?api_key=")
                .append(gifApiID)
                .append("&tag=")
                .append(tag);
        return URI.create(builderURL.toString());
    }}
