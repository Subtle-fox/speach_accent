package andyanika.speechaccent;

import android.content.Context;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by kolpakov on 15/04/16.
 */
public class AccentAdapter extends ArrayAdapter {
    String[] accentIds;
    String[] accentNames;

    List<Pair<Integer, String>> ids;

    public AccentAdapter(Context context, int resource, List ids) {
        super(context, resource, ids);
        this.ids = ids;
        accentIds = context.getResources().getStringArray(R.array.accent_ids);
        accentNames = context.getResources().getStringArray(R.array.accent_list);
    }

    public String getAccentFileName(int position) {
        return ids.get(position).second;
    }

    @Override
    public Object getItem(int position) {
        return accentNames[ids.get(position).first];
    }
}
