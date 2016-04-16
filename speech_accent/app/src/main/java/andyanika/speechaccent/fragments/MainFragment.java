package andyanika.speechaccent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andyanika.speechaccent.MainActivity;
import andyanika.speechaccent.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Andrey Kolpakov on 11.04.2016
 * for It-Atlantic
 */
public class MainFragment extends InterchangableFragment {
    @OnClick(R.id.btn_listen)
    public void onListen() {
        changeFragmentListener.onChange(MainActivity.FRAGMENT_LISTEN);
    }

    @OnClick(R.id.btn_record)
    public void onRecord() {
        changeFragmentListener.onChange(MainActivity.FRAGMENT_RECORD);
    }

    @OnClick(R.id.my_results)
    public void onResult() {
        changeFragmentListener.onChange(MainActivity.FRAGMENT_RESULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }
}
