package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.app.exception.AndroidException;

/**
 * Created by Thomas Reno on 13.03.2016.
 */
public abstract class MyTimestampFragment extends Fragment {

    public static MyTimestampFragment newInstance(String tagFragment) throws AndroidException {
        if (null == tagFragment) {
            return null;
        }
        MyTimestampFragment fragment;
        switch (tagFragment) {
            case AuftraggeberdatenFragment.TAG:
                fragment = new AuftraggeberdatenFragment();
                break;
            case AuftragsdatenFragment.TAG:
                fragment = new AuftragsdatenFragment();
                break;
            case BenutzerdatenFragment.TAG:
                fragment = new BenutzerdatenFragment();
                break;
            case MainFragment.TAG:
                fragment = new MainFragment();
                break;
            case ProjektdatenFragment.TAG:
                fragment = new ProjektdatenFragment();
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

    protected TextWatcher getTextWatcherForEditText(final EditText view, final Pattern pattern,
                                                  final String errorMessage) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null != pattern) {
                    boolean valid = !pattern.matcher(String.valueOf(s)).find();
                    view.setError(!valid ? errorMessage : null);
                }
                setEnableButtonSubmit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        };
    }

    protected boolean hasStringValue(String input) {
        return null != input && !"null".equals(input) && !input.isEmpty();
    }

    protected abstract void setEnableButtonSubmit();
}
