package andyanika.speechaccent.network;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by Andrey Kolpakov on 16.04.2016
 * for It-Atlantic
 */
public class DownloadTask extends AsyncTask<Void, Void, LanguageListJson> {
    @Override
    protected LanguageListJson doInBackground(Void... params) {
        try {
            //String res = new DownloadLanguageList().downloadLanguageList();
            //Log.i("NWK", res);

            //LanguageListJson result = new Gson().fromJson(res, LanguageListJson.class);
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
