package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NeuerAuftragFragment extends MyTimestampFragment implements SelectionDialogFragment.Callback {

    public static final String TAG = "NeuerAuftragFragment";

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_neuerauftrag, container, false);
        this.auftrag = ((NewMissionActivity) getActivity()).getAuftrag();
        initialize();
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String auftraggeber = this.auftrag.getAuftraggeber().getFirma();
        this.editTextAuftraggeber.setText(auftraggeber);
        setEnableButtonSubmit();
    }

    @Override
    public void onSelected(String result) {
        this.editTextAuftraggeber.setText(result);
    }

    private Button buttonSubmit;

    private TextInputEditText editTextAuftraggeber;

    private View view;

    private Auftrag auftrag;

    public NeuerAuftragFragment() {
        super();
    }

    private TextWatcher getTextWatcherForEditText(final Pattern pattern, final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null != pattern) {
                    boolean valid = !pattern.matcher(String.valueOf(s)).find();
                    editText.setError(!valid ? TEXT_MESSAGE_ERROR : null);
                }
                setEnableButtonSubmit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        };
    }

    protected void initialize() {
        getActivity().setTitle("Neuer Auftrag");
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitNeuerAuftrag);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewMissionActivity) getActivity()).onSubmit(NeuerAuftragFragment.this.auftrag);
            }
        });
        this.editTextAuftraggeber = (TextInputEditText) this.view.findViewById(R.id.editTextNeuerAuftragAuftraggeber);
        this.editTextAuftraggeber.setFocusable(false);
        this.editTextAuftraggeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String[] itemList = ((NewMissionActivity) getActivity()).getArrayAuftraggeberFirma();
                if (null != itemList) {
                    SelectionDialogFragment dialogFragment = SelectionDialogFragment.newInstance("Auftraggeber auswählen", itemList);
                    dialogFragment.setTargetFragment(NeuerAuftragFragment.this, 1);
                    ((NewMissionActivity) getActivity()).showDialogFragment(dialogFragment, "selection-auftraggeber");
                }
            }
        });
        this.editTextAuftraggeber.addTextChangedListener(getTextWatcherForEditText(null, this.editTextAuftraggeber));

    }

    private void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(true);
    }

    public interface Callback {

        void onSubmit(Auftrag auftrag);
    }
}
