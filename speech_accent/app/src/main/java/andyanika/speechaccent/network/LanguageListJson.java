package andyanika.speechaccent.network;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class LanguageListJson {
    List<SimpleLanguage> languages;

    class SimpleLanguage {
        String isoName;
        String text;
        Map<String, String> accents;
    }
}
