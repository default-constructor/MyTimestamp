package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Person;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface FragmentListener {
        void onSubmit(Person person) throws AppException;
    }

    private String fragmentTag;

    public static SettingsFragment newInstance(String tagFragment) {
        if (null == tagFragment) {
            return null;
        }
        SettingsFragment fragment;
        switch (tagFragment) {
            case AuftraggeberdatenFragment.TAG:
                fragment = new AuftraggeberdatenFragment();
                break;
            default:
                fragment = new BenutzerdatenFragment();
                tagFragment = BenutzerdatenFragment.TAG;
        }
        fragment.setFragmentTag(tagFragment);
        return fragment;
    }

    public String getFragmentTag() {
        return this.fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }
}
