package andyanika.speechaccent.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import andyanika.speechaccent.LanguageAdapter;
import andyanika.speechaccent.MainActivity;
import andyanika.speechaccent.MediaPlayback;
import andyanika.speechaccent.PlayerCallback;
import andyanika.speechaccent.R;
import andyanika.speechaccent.RingChart;
import andyanika.speechaccent.SoundRecorder;
import andyanika.speechaccent.utils.SampleTextBuilder;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Andrey Kolpakov on 11.04.2016
 * for It-Atlantic
 */
public class ResultFragment extends InterchangableFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }
}
