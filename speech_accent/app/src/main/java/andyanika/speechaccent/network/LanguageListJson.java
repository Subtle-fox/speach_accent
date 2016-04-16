package andyanika.speechaccent.network;

import java.util.List;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class LanguageListJson {
    SimpleLanguage languages;

    class SimpleLanguage {
        String name;
        List<String> accents;
    }
}
