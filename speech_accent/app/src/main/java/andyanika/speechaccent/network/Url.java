package andyanika.speechaccent.network;

import okhttp3.OkHttpClient;

/**
 * Created by Andrey Kolpakov on 17.04.2016
 * for It-Atlantic
 */
public class Url {
    static final String LANGUAGE = "language";
    static final String RATE = "rate";
    static final String RECORD = "record";

    private static final String BASE_URL = "http://env-7092897.jelastic.regruhosting.ru/speechAccent/";

    private static OkHttpClient client;
    static OkHttpClient getClient() {
        if (client != null) {
            client = new OkHttpClient();
        }
        return client;
    }

    public static String getUrl(String language) {
        return BASE_URL + language;
    }
}
