package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Fragment;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import de.defaultconstructor.mytimestamp.app.exception.AndroidException;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class MyTimestampFragment extends Fragment {

    public static MyTimestampFragment newInstance(String tagFragment) throws AndroidException {
        if (null == tagFragment) {
            return null;
        }
        MyTimestampFragment fragment;
        switch (tagFragment) {
            case AuftraggeberdatenFragment.TAG:
                fragment = new AuftraggeberdatenFragment();
                break;
            case BenutzerdatenFragment.TAG:
                fragment = new BenutzerdatenFragment();
                break;
            case MainFragment.TAG:
                fragment = new MainFragment();
                break;
            case NeuerAuftragFragment.TAG:
                fragment = new NeuerAuftragFragment();
                break;
            default:
                throw new AndroidException("No Fragment " + tagFragment + " found.");
        }
        fragment.setFragmentTag(tagFragment);
        return fragment;
    }

    private String fragmentTag;

    public String getFragmentTag() {
        return this.fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }
}
