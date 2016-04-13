package andyanika.speechaccent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListenerFragment extends InterchangableFragment {
    private boolean isPaused = true;
    private Playback playbackTask;

    @InjectView(R.id.ring_chart)
    RingChart ringChart;

    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    @InjectView(R.id.btn_play)
    Button playBtn;

    @OnClick(R.id.btn_play)
    void onPlayClicked() {
        if (playbackTask != null) {
            playbackTask.cancel(true);
            playbackTask = null;
        }

        if (isPaused) {
            playbackTask = new Playback();
            playbackTask.execute(seekBar.getProgress());
        }

        changePlayButtonView(!isPaused);
    }

    @InjectView(R.id.spinner_language)
    Spinner spinnerLanguage;

    @InjectView(R.id.spinner_accent)
    Spinner spinnerAccent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_listen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        spinnerLanguage.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_list)));
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAccentList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void changePlayButtonView(boolean isPlaying) {
        this.isPaused = isPlaying;
        if (isPlaying) {
            playBtn.setBackgroundResource(R.drawable.btn_play);
        } else {
            playBtn.setBackgroundResource(R.drawable.btn_pause);
        }
    }

    private void updateAccentList(int languageId) {
        spinnerAccent.setVisibility(View.VISIBLE);

        ArrayList<String> accents = new ArrayList<>();
        for (String accent : getResources().getStringArray(R.array.accent_list)) {
            if (hasAccent(languageId, accent)) {
                accents.add(accent);
            }
        }

        spinnerAccent.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, accents));
    }

    private boolean hasAccent(int languageId, String accent) {
        return true;
    }


    class Playback extends AsyncTask<Integer, Integer, Void> {
        int progress = 1;

        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = params[0]; i <= 100; i++) {
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            seekBar.setProgress(values[0]);
            ringChart.setValue(values[0]);
        }
    }
}
