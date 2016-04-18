package andyanika.speechaccent.network;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class DownloadLanguages {
    public String downloadLanguageList() throws IOException {
        String url = Url.getUrl(Url.LANGUAGE);
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
