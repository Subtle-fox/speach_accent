package andyanika.speechaccent.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import andyanika.speechaccent.OnChangeFragmentListener;

/**
 * Created by kolpakov on 11/04/16.
 */
public abstract class InterchangableFragment extends Fragment {
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }
}
