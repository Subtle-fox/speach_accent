package andyanika.speechaccent.network;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Andrey Kolpakov on 17.04.2016
 * for It-Atlantic
 */
public class SendRate {
    OkHttpClient client = new OkHttpClient();

    public String send(final String rate, final String fileName) throws IOException {
        String url = Url.getUrl(Url.RATE);

        RequestBody formBody = new FormBody.Builder()
                .add("rate", rate)
                .add("file", fileName)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        ResponseBody body = response.body();
        String raw = body.string();
        body.close();
        return raw;
    }
}
