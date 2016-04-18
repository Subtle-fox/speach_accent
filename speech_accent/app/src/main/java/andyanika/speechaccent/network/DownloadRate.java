package andyanika.speechaccent.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Andrey Kolpakov on 17.04.2016
 * for It-Atlantic
 */
public class DownloadRate {
    public String run(final String language, final String fileName) throws IOException {
        HttpUrl url = HttpUrl.parse(Url.getUrl(Url.RATE)).newBuilder()
                .addQueryParameter("language", language)
                .addQueryParameter("filename", fileName).build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = Url.getClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        ResponseBody body = response.body();
        String raw = body.string();
        body.close();
        return raw;
    }
}
