package andyanika.speechaccent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnChangeFragmentListener {
    final static int FRAGMENT_MAIN = 2100;
    final static int FRAGMENT_LISTEN = 2110;
    final static int FRAGMENT_RECORD = 2120;

    int currentFragmentId = FRAGMENT_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, new MainFragment(), String.valueOf(FRAGMENT_MAIN))
                .commit();
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
        //selectItem(fragmentId);
        Fragment fr = getFragment(fragmentId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fr, String.valueOf(fragmentId))
                .commit();
    }

    private void selectItem(int fragmentId) {
        if (fragmentId == currentFragmentId) {
            return;
        }

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
            case FRAGMENT_MAIN:
            default:
                return new MainFragment();
        }
    }
}
