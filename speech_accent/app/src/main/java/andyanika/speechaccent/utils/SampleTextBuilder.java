package andyanika.speechaccent.utils;

import android.content.Context;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;

import andyanika.speechaccent.R;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class SampleTextBuilder {
    public static int getStringResource(String languageId) {
        @StringRes int sampleTextId = R.string.ru_sample_text;
        switch (languageId) {
            case "eng":
                sampleTextId = R.string.en_sample_text;
                break;
            case "rus":
                sampleTextId = R.string.ru_sample_text;
                break;
            case "china":
                sampleTextId = R.string.ch_sample_text;
                break;
            case "span":
                sampleTextId = R.string.sp_sample_text;
                break;
            case "french":
                sampleTextId = R.string.fr_sample_text;
                break;
            case "italy":
                sampleTextId = R.string.italian_sample_text;
                break;
        }
        return sampleTextId;
    }
}
