package andyanika.speechaccent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListenerFragment extends InterchangableFragment {
    private static final String PLAY_PROGRESS = "play_progress";

    private boolean isPaused = true;
    private Playback playbackTask;
    private int progress;
    private MediaPlayback mediaPlayback;
    private AccentAdapter accentAdapter;

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
            mediaPlayback.stop();
        }

        if (isPaused) {
            String languageId = getResources().getStringArray(R.array.language_ids)[spinnerLanguage.getSelectedItemPosition()];
            String fileName = accentAdapter.getAccentFileName(spinnerAccent.getSelectedItemPosition());
            mediaPlayback.play(languageId, fileName);
            playbackTask = new Playback();
            playbackTask.execute(progress);
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

        if (savedInstanceState != null) {
            progress = savedInstanceState.getInt(PLAY_PROGRESS);
            seekBar.setProgress(progress);
            ringChart.setProgress(progress);
        }

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

        mediaPlayback = new MediaPlayback(getActivity());
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

        String[] accentIds = getResources().getStringArray(R.array.accent_ids);
        accentAdapter = new AccentAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, getAvailableAccentIds(languageId, accentIds));
        spinnerAccent.setAdapter(accentAdapter);
    }

    private ArrayList<Pair<Integer, String>> getAvailableAccentIds(int languageId, String[] accentIds) {
        String languageFolder = getResources().getStringArray(R.array.language_ids)[languageId];
        ArrayList<Pair<Integer, String>> result = new ArrayList<>();
        try {
            String[] accentFiles = getResources().getAssets().list(languageFolder);
            for (int i = 0; i < accentFiles.length; i++) {
                for (int j = 0; j < accentIds.length; j++) {
                    if (accentFiles[i].startsWith(accentIds[j], languageFolder.length() + 1)) {
                        result.add(Pair.create(j, accentFiles[i]));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAY_PROGRESS, progress);
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
            progress = values[0];
            seekBar.setProgress(progress);
            ringChart.setProgress(progress);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayback.stop();

        changePlayButtonView(false);
        if (playbackTask != null) {
            playbackTask.cancel(false);
        }
    }

    private String getCurrentLanguageFolder(int selectedLanguage) {
        return getResources().getStringArray(R.array.language_ids)[selectedLanguage];
    }
}
