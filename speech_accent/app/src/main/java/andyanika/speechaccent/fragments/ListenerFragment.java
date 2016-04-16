package andyanika.speechaccent.fragments;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import andyanika.speechaccent.AccentAdapter;
import andyanika.speechaccent.MediaPlayback;
import andyanika.speechaccent.PlayerCallback;
import andyanika.speechaccent.R;
import andyanika.speechaccent.RingChart;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListenerFragment extends InterchangableFragment {
    private static final String PLAY_PROGRESS = "play_progress";

    private boolean isPaused = true;
    private int progress;
    private MediaPlayback mediaPlayback;
    private AccentAdapter accentAdapter;

    @InjectView(R.id.ring_chart)
    RingChart ringChart;

    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    @InjectView(R.id.btn_play)
    Button playBtn;

    @InjectView(R.id.textSampleText)
    TextView sampleText;

    @OnClick(R.id.btn_play)
    void onPlayClicked() {
        if (isPaused) {
            play();
        } else {
            pause();
        }
        this.isPaused = !isPaused;
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
        }
        seekBar.setProgress(progress);
        seekBar.setClickable(false);
        ringChart.setProgress(progress);

        spinnerLanguage.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_list)));
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAccentList(position);

                @StringRes int sampleTextId = R.string.ru_sample_text;
                switch (getResources().getStringArray(R.array.language_ids)[position]) {
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
                }
                sampleText.setText(sampleTextId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerAccent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stop();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        playBtn.setBackgroundResource(R.drawable.btn_play);
        mediaPlayback = new MediaPlayback(getActivity(), playerCallback);
    }

    private void play() {
        if (mediaPlayback.isPaused()) {
            mediaPlayback.resume();
        } else {
            stop();

            String languageId = getResources().getStringArray(R.array.language_ids)[spinnerLanguage.getSelectedItemPosition()];
            String fileName = accentAdapter.getAccentFileName(spinnerAccent.getSelectedItemPosition());
            mediaPlayback.play(languageId, fileName);
        }
        playBtn.setBackgroundResource(R.drawable.btn_pause);
    }

    private void pause() {
        mediaPlayback.pause();
        playBtn.setBackgroundResource(R.drawable.btn_play);
    }

    private void stop() {
        mediaPlayback.stop();
        seekBar.setProgress(0);
        ringChart.setProgress(0);
        playBtn.setBackgroundResource(R.drawable.btn_play);
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

    PlayerCallback playerCallback = new PlayerCallback() {
        int duration;

        @Override
        public void onStarted(int duration) {
            this.duration = duration;
            seekBar.setProgress(0);
            ringChart.setProgress(0);
            playBtn.setBackgroundResource(R.drawable.btn_pause);
        }

        @Override
        public void onPlaying(final int position) {
            final int progress = position * 100 / duration;
            seekBar.setProgress(progress);
            ringChart.post(new Runnable() {
                @Override
                public void run() {
                    ringChart.setProgress(progress);
                }
            });
        }

        @Override
        public void onFinished() {
            seekBar.setProgress(0);
            ringChart.setProgress(0);
            playBtn.setBackgroundResource(R.drawable.btn_play);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        stop();
    }

    private void markText() {
        String text = sampleText.getText().toString();



        Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        spanText.setSpan(new BackgroundColorSpan(0xFFFFFF00), 14, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sampleText.setText(spanText);
    }
}