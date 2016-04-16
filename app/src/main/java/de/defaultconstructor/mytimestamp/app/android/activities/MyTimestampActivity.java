package de.defaultconstructor.mytimestamp.app.android.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

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

    protected String tagCurrentFragment;

    public MyTimestampFragment getCurrentFragment() {
        return mapFragments.get(tagCurrentFragment);
    }

    public void showDialogFragment(DialogFragment dialogFragment, String id) {
        FragmentManager manager = getFragmentManager();
        dialogFragment.show(manager, "dialog-" + id);
    }

    public void renderFragment(String tag, int containerViewId) throws AndroidException {
        renderFragment(tag, containerViewId, false);
    }

    public void renderFragment(String tag, int containerViewId, boolean forceNewInstance) throws AndroidException {
        if (!this.mapFragments.containsKey(tag) || forceNewInstance) {
            this.mapFragments.put(tag, MyTimestampFragment.newInstance(tag));
        }
        renderFragment(this.mapFragments.get(tag), containerViewId);
    }

    private void renderFragment(Fragment fragment, int containerViewId) {
        if (null == fragment) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.tagCurrentFragment = ((MyTimestampFragment) fragment).getFragmentTag();
    }
}
