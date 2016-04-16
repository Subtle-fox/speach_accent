package andyanika.speechaccent;

import android.content.Context;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolpakov on 15/04/16.
 */
public class LanguageAdapter extends ArrayAdapter {
    ArrayList<Pair<String, String>> languages = new ArrayList<>();

    public LanguageAdapter(Context context, int resource, String[] languageIds) {
        super(context, resource, languageIds);
        String[] languageNames = context.getResources().getStringArray(R.array.language_list);
        for (int i = 0; i < languageIds.length; i++) {
            languages.add(Pair.create(languageIds[i], languageNames[i]));
        }
    }

    public String getLanguageId(int position) {
        return languages.get(position).first;
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position).second;
    }
}
