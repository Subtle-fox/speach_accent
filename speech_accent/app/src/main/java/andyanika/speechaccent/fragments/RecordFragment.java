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
public class RecordFragment extends InterchangableFragment {
    private static final String RECORD_PROGRESS = "record_progress";
    private boolean isRecorded;
    private int progress;
    private MediaPlayback mediaPlayback;
    private SoundRecorder soundRecorder;

    @InjectView(R.id.textTimer)
    TextView txtTimer;

    @InjectView(R.id.ring_chart)
    RingChart ringChart;

    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    @InjectView(R.id.btn_record)
    Button recordBtn;

    @InjectView(R.id.btn_play)
    Button btnPlay;

    @InjectView(R.id.textSampleText)
    TextView sampleText;

    @InjectView(R.id.btn_send)
    Button btnSend;

    @OnClick(R.id.btn_send)
    void onSendClicked() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Ваша запись отправлена для оценки")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeFragmentListener.onChange(MainActivity.FRAGMENT_MAIN);
                    }
                }).show();
    }

    @OnClick(R.id.btn_record)
    void onRecordClicked() {
        if (isRecorded) {
            stopRecord();
        } else {
            record();
        }
        this.isRecorded = !isRecorded;
    }

    @OnClick(R.id.btn_play)
    void onPlayClicked() {
        mediaPlayback.playRecorded(soundRecorder.getFileName());
    }

    private void record() {
        recordBtn.setBackgroundResource(R.drawable.btn_stop_record);
        btnSend.setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        soundRecorder.startRecording();
    }

    private void stopRecord() {
        recordBtn.setBackgroundResource(R.drawable.btn_record);
        btnSend.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.VISIBLE);
        seekBar.setVisibility(View.VISIBLE);
        recordBtn.setVisibility(View.GONE);
        txtTimer.setVisibility(View.GONE);
        soundRecorder.stopRecording();
    }

    @InjectView(R.id.spinner_native)
    Spinner spinnerNativeLanguage;

    @InjectView(R.id.spinner_record_language)
    Spinner spinnerRecordLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        if (savedInstanceState != null) {
            progress = savedInstanceState.getInt(RECORD_PROGRESS);
        }
        seekBar.setProgress(progress);
        ringChart.setProgress(progress);

        spinnerNativeLanguage.setAdapter(new LanguageAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.language_ids)));

        spinnerRecordLanguage.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_list)));
        spinnerRecordLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sampleText.setText(
                        SampleTextBuilder.getStringResource(getResources().getStringArray(R.array.language_ids)[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        soundRecorder = new SoundRecorder(getActivity(), new PlayerCallback() {
            long startTime;

            @Override
            public void onStarted(int duration) {
                startTime = System.currentTimeMillis();
            }

            @Override
            public void onPlaying(int position) {
                final long diff = System.currentTimeMillis() - startTime;
                final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

                txtTimer.post(new Runnable() {
                    @Override
                    public void run() {
                        txtTimer.setText(sdf.format(new Date(diff)));
                    }
                });
            }

            @Override
            public void onFinished() {

            }
        });
        mediaPlayback = new MediaPlayback(getActivity(), playerCallback);
    }

    PlayerCallback playerCallback = new PlayerCallback() {
        int duration;

        @Override
        public void onStarted(int duration) {
            this.duration = duration;
            seekBar.setProgress(0);
            ringChart.setProgress(0);
            btnPlay.setBackgroundResource(R.drawable.btn_pause);
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
            btnPlay.setBackgroundResource(R.drawable.btn_play);
        }
    };


    @Override
    public void onDestroy() {
        if (soundRecorder != null) {
            soundRecorder.stopRecording();
            soundRecorder = null;
        }


        if (mediaPlayback != null) {
            mediaPlayback.stop();
            mediaPlayback = null;
        }
    }
}
