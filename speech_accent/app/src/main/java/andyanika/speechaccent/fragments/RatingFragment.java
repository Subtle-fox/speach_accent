package andyanika.speechaccent.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import andyanika.speechaccent.MainActivity;
import andyanika.speechaccent.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
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
    }

    @InjectView(R.id.star_1)
    ImageView star1;

    @InjectView(R.id.star_2)
    ImageView star2;

    @InjectView(R.id.star_3)
    ImageView star3;

    @InjectView(R.id.star_4)
    ImageView star4;

    @InjectView(R.id.star_5)
    ImageView star5;

    @OnClick(R.id.star_1)
    void onStar1Clicked() {
        star1.setBackgroundResource(R.drawable.star);
        star2.setBackgroundResource(R.drawable.star_gr);
        star3.setBackgroundResource(R.drawable.star_gr);
        star4.setBackgroundResource(R.drawable.star_gr);
        star5.setBackgroundResource(R.drawable.star_gr);
    }

    @OnClick(R.id.star_2)
    void onStar2Clicked() {
        star1.setBackgroundResource(R.drawable.star);
        star2.setBackgroundResource(R.drawable.star);
        star3.setBackgroundResource(R.drawable.star_gr);
        star4.setBackgroundResource(R.drawable.star_gr);
        star5.setBackgroundResource(R.drawable.star_gr);
    }

    @OnClick(R.id.star_3)
    void onStar3Clicked() {
        star1.setBackgroundResource(R.drawable.star);
        star2.setBackgroundResource(R.drawable.star);
        star3.setBackgroundResource(R.drawable.star);
        star4.setBackgroundResource(R.drawable.star_gr);
        star5.setBackgroundResource(R.drawable.star_gr);
    }

    @OnClick(R.id.star_4)
    void onStar4Clicked() {
        star1.setBackgroundResource(R.drawable.star);
        star2.setBackgroundResource(R.drawable.star);
        star3.setBackgroundResource(R.drawable.star);
        star4.setBackgroundResource(R.drawable.star);
        star5.setBackgroundResource(R.drawable.star_gr);
    }

    @OnClick(R.id.star_5)
    void onStar5Clicked() {
        star1.setBackgroundResource(R.drawable.star);
        star2.setBackgroundResource(R.drawable.star);
        star3.setBackgroundResource(R.drawable.star);
        star4.setBackgroundResource(R.drawable.star);
        star5.setBackgroundResource(R.drawable.star);
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
