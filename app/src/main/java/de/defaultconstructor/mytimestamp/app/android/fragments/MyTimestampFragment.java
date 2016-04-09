package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Fragment;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class MyTimestampFragment extends Fragment {

    public static MyTimestampFragment newInstance(String tagFragment) {
        if (null == tagFragment) {
            return null;
        }
        MyTimestampFragment fragment;
        switch (tagFragment) {
            case AuftraggeberdatenFragment.TAG:
                fragment = new AuftraggeberdatenFragment();
                break;
            case NeuerAuftragFragment.TAG:
                fragment = new NeuerAuftragFragment();
                break;
            default:
                fragment = new BenutzerdatenFragment();
                tagFragment = BenutzerdatenFragment.TAG;
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
