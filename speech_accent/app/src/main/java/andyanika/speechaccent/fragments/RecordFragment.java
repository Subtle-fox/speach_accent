package andyanika.speechaccent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andyanika.speechaccent.MainActivity;
import andyanika.speechaccent.R;
import andyanika.speechaccent.fragments.InterchangableFragment;

/**
 * Created by Andrey Kolpakov on 11.04.2016
 * for It-Atlantic
 */
public class RecordFragment extends InterchangableFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentListener.onChange(MainActivity.FRAGMENT_MAIN);
            }
        });
    }
}
