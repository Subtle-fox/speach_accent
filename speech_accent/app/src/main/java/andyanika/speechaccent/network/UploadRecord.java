package andyanika.speechaccent.network;

import java.io.File;
import java.io.IOException;

import andyanika.speechaccent.SoundRecorder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class UploadRecord {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String uploadRecord(String json) throws IOException {
        String url = "apps.engine/download_lang.json";

        File file = new File("kh");
        RequestBody body = RequestBody.create(MediaType.parse("audio/mpeg3"), file);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
