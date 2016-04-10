package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.enumeration.Berechnungsfaktor;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
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
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.auftrag = ((NewMissionActivity) getActivity()).getAuftrag();
        initialize();
        String auftraggeber = this.auftrag.getAuftraggeber().getFirma();
        this.editTextAuftraggeber.setText(auftraggeber);
        setEnableButtonSubmit();
    }

    @Override
    public void onSelected(String tag, String result) {
        switch (tag) {
            case "dialog-selection-auftraggeber":
                this.editTextAuftraggeber.setText(result);
                this.auftrag.setAuftraggeber(((NewMissionActivity) getActivity()).getAuftraggeber(result));
                break;
            case "dialog-selection-berechnungsfaktor":
                this.editTextBerechnungsfaktor.setText(result);
                break;
            default:
                //
        }
    }

    private Button buttonSubmit;
    private Button buttonNeuerAuftraggeber;

    private TextInputEditText editTextAuftraggeber;
    private TextInputEditText editTextArbeitsentgelt;
    private TextInputEditText editTextBerechnungsfaktor;
    private TextInputEditText editTextNotiz;

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

    private boolean hasStringValue(String input) {
        return null != input && !input.isEmpty();
    }

    protected void initialize() {
        getActivity().setTitle("Neuer Auftrag");
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitNeuerAuftrag);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapAuftragsdaten();
                ((NewMissionActivity) getActivity()).onSubmit(NeuerAuftragFragment.this.auftrag);
            }
        });
        this.editTextAuftraggeber = (TextInputEditText) this.view.findViewById(R.id.editTextNeuerAuftragAuftraggeber);
        this.editTextAuftraggeber.setFocusable(false);
        this.editTextAuftraggeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] itemList = ((NewMissionActivity) getActivity()).getArrayAuftraggeberFirma();
                if (null != itemList && 0 < itemList.length) {
                    SelectionDialogFragment dialogFragment = SelectionDialogFragment.newInstance("Auftraggeber auswählen", itemList);
                    dialogFragment.setTargetFragment(NeuerAuftragFragment.this, 1);
                    ((NewMissionActivity) getActivity()).showDialogFragment(dialogFragment, "selection-auftraggeber");
                } else {
                    try {
                        ((NewMissionActivity) getActivity()).renderFragment(AuftraggeberdatenFragment.TAG, R.id.activityNewMissionWrapper, true);
                    } catch (AndroidException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        });
        this.editTextAuftraggeber.addTextChangedListener(getTextWatcherForEditText(null, this.editTextAuftraggeber));
        this.buttonNeuerAuftraggeber = (Button) this.view.findViewById(R.id.buttonNeuerAuftragNeuerAuftraggeber);
        this.buttonNeuerAuftraggeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((NewMissionActivity) getActivity()).renderFragment(AuftraggeberdatenFragment.TAG, R.id.activityNewMissionWrapper, true);
                } catch (AndroidException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        this.editTextArbeitsentgelt = (TextInputEditText) this.view.findViewById(R.id.editTextNeuerAuftragArbeitsentgelt);
        this.editTextBerechnungsfaktor = (TextInputEditText) this.view.findViewById(R.id.editTextNeuerAuftragBerechnungsfaktor);
        this.editTextBerechnungsfaktor.setFocusable(false);
        this.editTextBerechnungsfaktor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectionDialogFragment dialogFragment = SelectionDialogFragment.newInstance("Berechnungsfaktor auswählen", getResources().getStringArray(R.array.listBerechnungsfaktor));
                dialogFragment.setTargetFragment(NeuerAuftragFragment.this, 2);
                ((NewMissionActivity) getActivity()).showDialogFragment(dialogFragment, "selection-berechnungsfaktor");
            }
        });
        this.editTextNotiz = (TextInputEditText) this.view.findViewById(R.id.editTextNeuerAuftragNotiz);
    }

    private void mapAuftragsdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextArbeitsentgelt.getText()))) {
            this.auftrag.setArbeitsentgelt(BigDecimal.valueOf(Double.parseDouble(value)));
        }
        if (hasStringValue(value = String.valueOf(this.editTextBerechnungsfaktor.getText()))) {
            this.auftrag.setBerechnungsfaktor(Berechnungsfaktor.getByBezeichnung(value));
        }
        if (hasStringValue(value = String.valueOf(this.editTextNotiz.getText()))) {
            this.auftrag.setNotiz(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextAuftraggeber.getText()))) {
            this.auftrag.setAuftraggeber(((NewMissionActivity) getActivity()).getAuftraggeber(value));
        }
    }

    private void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(true);
    }

    public interface Callback {
        void onSubmit(Auftrag auftrag);
    }
}
