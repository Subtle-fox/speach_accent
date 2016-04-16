package andyanika.speechaccent.network;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class DownloadLanguageList {
    OkHttpClient client = new OkHttpClient();

    public LanguageListJson downloadLanguageList() throws IOException {
        String url = "apps.engine/download_lang.json";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String raw = response.body().string();

        LanguageListJson result = new Gson().fromJson(raw, LanguageListJson.class);
        return result;
    }
}
