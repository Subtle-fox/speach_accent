package andyanika.speechaccent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import andyanika.speechaccent.fragments.ListenerFragment;
import andyanika.speechaccent.fragments.MainFragment;
import andyanika.speechaccent.fragments.RecordFragment;
import andyanika.speechaccent.fragments.ResultFragment;
import andyanika.speechaccent.network.DownloadTask;

public class MainActivity extends AppCompatActivity implements OnChangeFragmentListener {
    public final static int FRAGMENT_MAIN = 2100;
    public final static int FRAGMENT_LISTEN = 2110;
    public final static int FRAGMENT_RECORD = 2120;
    public final static int FRAGMENT_RESULT = 2030;

    private static final String FRAGMENT_ID = "current_fragment_id";

    int currentFragmentId = FRAGMENT_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentFragmentId = savedInstanceState.getInt(FRAGMENT_ID, FRAGMENT_MAIN);
        } else {
            selectItem(currentFragmentId);
        }

        new DownloadTask().execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_ID, currentFragmentId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChange(int fragmentId) {
        currentFragmentId = fragmentId;

        Fragment fr = getFragment(fragmentId);
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.content_frame, fr, String.valueOf(fragmentId));
        if (fragmentId != FRAGMENT_MAIN) {
            tr.addToBackStack(null);
        }
        tr.commit();
    }

    private void selectItem(int fragmentId) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment frToShow = fragmentManager.findFragmentByTag(String.valueOf(fragmentId));
        Fragment frToHide = fragmentManager.findFragmentByTag(String.valueOf(currentFragmentId));

        FragmentTransaction tr = fragmentManager.beginTransaction();
        if (frToHide != null) {
            tr.hide(frToHide);
        }

        if (frToShow != null) {
            tr.show(frToShow);
        } else {
            tr.add(R.id.content_frame, getFragment(fragmentId), String.valueOf(fragmentId));
        }
        tr.commit();
        currentFragmentId = fragmentId;
    }

    private Fragment getFragment(int fragmentId) {
        switch (fragmentId) {
            case FRAGMENT_LISTEN:
                return new ListenerFragment();
            case FRAGMENT_RECORD:
                return new RecordFragment();
            case FRAGMENT_RESULT:
                return new ResultFragment();
            case FRAGMENT_MAIN:
            default:
                return new MainFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentId == FRAGMENT_MAIN) {
            super.onBackPressed();
        } else {
            onChange(FRAGMENT_MAIN);
        }
    }
}
