package andyanika.speechaccent.network;

/**
 * Created by Andrey Kolpakov on 17.04.2016
 * for It-Atlantic
 */
public class Url {
    static final String LANGUAGE = "language";
    static final String RATE = "rate";
    static final String RECORD = "record";

    private static final String BASE_URL = "http://env-7092897.jelastic.regruhosting.ru/speechAccent/";

    public static String getUrl(String language) {
        return BASE_URL + language;
    }
}
