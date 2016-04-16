package andyanika.speechaccent.fragments;

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

import andyanika.speechaccent.LanguageAdapter;
import andyanika.speechaccent.R;
import andyanika.speechaccent.RingChart;
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

    @InjectView(R.id.ring_chart)
    RingChart ringChart;

    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    @InjectView(R.id.btn_record)
    Button recordBtn;

    @InjectView(R.id.textSampleText)
    TextView sampleText;

    @InjectView(R.id.btn_send)
    Button btnSend;

    @OnClick(R.id.btn_record)
    void onPlayClicked() {
        if (isRecorded) {
            save();
        } else {
            record();
        }
        this.isRecorded = !isRecorded;
    }

    private void record() {
        recordBtn.setBackgroundResource(R.drawable.btn_stop_record);
        btnSend.setVisibility(View.GONE);
    }

    private void save() {
        recordBtn.setBackgroundResource(R.drawable.btn_record);
        btnSend.setVisibility(View.VISIBLE);
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
    }
}
