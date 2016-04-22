package de.defaultconstructor.mytimestamp.app.android.activities;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import de.defaultconstructor.mytimestamp.app.android.fragments.MyTimestampFragment;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class MyTimestampActivity extends AppCompatActivity {

    private static final String TAG = "MyTimestampActivity";

    protected Map<String, MyTimestampFragment> mapFragments = new HashMap<>();

    public void showDialogFragment(DialogFragment dialogFragment, String id) {
        dialogFragment.show(getSupportFragmentManager(), "dialog-" + id);
    }

    public void renderFragment(String tag, int containerViewId, boolean forceNewInstance, boolean addToBackStack) throws AndroidException {
        if (!this.mapFragments.containsKey(tag) || forceNewInstance) {
            this.mapFragments.put(tag, MyTimestampFragment.newInstance(tag));
        }
        renderFragment(this.mapFragments.get(tag), containerViewId, addToBackStack);
    }

    public void renderFragment(String tag, int containerViewId, boolean forceNewInstance) throws AndroidException {
        renderFragment(tag, containerViewId, forceNewInstance, false);
    }

    private void renderFragment(Fragment fragment, int containerViewId, boolean addToBackStack) {
        if (null == fragment) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}
