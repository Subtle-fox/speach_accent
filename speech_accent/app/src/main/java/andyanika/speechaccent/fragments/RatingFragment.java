package andyanika.speechaccent.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andyanika.speechaccent.MainActivity;
import andyanika.speechaccent.OnChangeFragmentListener;
import andyanika.speechaccent.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andrey Kolpakov on 17.04.2016
 * for It-Atlantic
 */
public class RatingFragment extends DialogFragment {
    protected OnChangeFragmentListener changeFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragmentListener = (OnChangeFragmentListener) getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            changeFragmentListener = (OnChangeFragmentListener) activity;
        }
    }

    @OnClick(R.id.btn_ok)
    void onOkClicked() {
        getDialog().dismiss();
        changeFragmentListener.onChange(MainActivity.FRAGMENT_MAIN);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClicked() {
        getDialog().dismiss();
        changeFragmentListener.onChange(MainActivity.FRAGMENT_MAIN);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_rate, container, false);
        ButterKnife.inject(this, view);

        getDialog().setTitle("Оцените произношение");
        return view;
    }
}
