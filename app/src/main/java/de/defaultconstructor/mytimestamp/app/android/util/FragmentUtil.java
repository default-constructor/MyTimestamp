package de.defaultconstructor.mytimestamp.app.android.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Thomas Reno on 24.04.2016.
 */
public class FragmentUtil {

    /**
     *
     * @param activity
     * @return
     */
    public static Fragment getFragment(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    public static void setFragment(FragmentActivity activity, Fragment fragment, int containerViewId, boolean addToBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}
