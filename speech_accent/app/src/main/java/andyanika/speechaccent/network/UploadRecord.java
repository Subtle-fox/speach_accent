package andyanika.speechaccent.network;

import java.io.File;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

        File file = new File("234");
        RequestBody body = RequestBody.create(MediaType.parse("audio/mpeg3"), file);

//        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

//        try {
//           RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM)
//                    .addFormDataPart("file", file.getName(),
//                            RequestBody.create(MediaType.parse("text/csv"), file))
//                    .addFormDataPart("some-field", "some-value")
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(serverURL)
//                    .post(requestBody)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//
//                @Override
//                public void onFailure(Request request, IOException e) {
//                    // Handle the error
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//                    if (!response.isSuccessful()) {
//                        // Handle the error
//                    }
//                    // Upload successful
//                }
//            });
//
//            return "";
//        } catch (Exception ex) {
//            // Handle the error
//        }
//        return "";
//    }
    }
}
