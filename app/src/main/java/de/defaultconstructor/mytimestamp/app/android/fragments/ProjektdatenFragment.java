package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.android.components.DateTimeEditText;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 17.04.2016.
 */
public class ProjektdatenFragment extends MyTimestampFragment {

    public static final String TAG = "ProjektdatenFragment";

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.projekt = new Projekt();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_projektdaten, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
        setEnableButtonSubmit();
    }

    @Override
    protected void setEnableButtonSubmit() {
        Log.d(TAG, "set enable button submit");
        Log.d(TAG, "Projektbeginn: " + String.valueOf(this.editTextBeginn.getText()));
        Log.d(TAG, "Projektende: " + String.valueOf(this.editTextEnde.getText()));
    }

    private Button buttonSubmit;

    private DateTimeEditText editTextBeginn;
    private TextInputEditText editTextBeschreibung;
    private DateTimeEditText editTextEnde;
    private TextInputEditText editTextName;
    private TextInputEditText editTextNummer;

    private View view;

    private Projekt projekt;

    private void initialize() {
        getActivity().setTitle("Projektdaten");
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitProjektdaten);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                mapProjektdaten();
                ((NewMissionActivity) getActivity()).onSubmit(ProjektdatenFragment.this.projekt);
            }
        });

        this.editTextBeginn = (DateTimeEditText) this.view.findViewById(R.id.editTextProjektdatenBeginn);
        EditText editTextBeginnDatum = this.editTextBeginn.getEditTextDate();
        editTextBeginnDatum.addTextChangedListener(getTextWatcherForEditText(editTextBeginnDatum, null, null));
        EditText editTextBeginnUhrzeit = this.editTextBeginn.getEditTextTime();
        editTextBeginnUhrzeit.addTextChangedListener(getTextWatcherForEditText(editTextBeginnUhrzeit, null, null));

        this.editTextBeschreibung = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenBeschreibung);
        this.editTextBeschreibung.addTextChangedListener(getTextWatcherForEditText(this.editTextBeschreibung, null, null));

        this.editTextEnde = (DateTimeEditText) this.view.findViewById(R.id.editTextProjektdatenEnde);
        EditText editTextEndeDatum = this.editTextEnde.getEditTextDate();
        editTextEndeDatum.addTextChangedListener(getTextWatcherForEditText(editTextEndeDatum, null, null));
        EditText editTextEndeUhrzeit = this.editTextEnde.getEditTextTime();
        editTextEndeUhrzeit.addTextChangedListener(getTextWatcherForEditText(editTextEndeUhrzeit, null, null));

        this.editTextName = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenName);
        this.editTextName.addTextChangedListener(getTextWatcherForEditText(this.editTextName, null, null));

        this.editTextNummer = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenNummer);
        this.editTextNummer.addTextChangedListener(getTextWatcherForEditText(this.editTextNummer, null, null));
    }

    private void mapProjektdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextBeginn.getText()))) {
            this.projekt.setBeginn(DateUtil.getDateFromString(value));
        }
        if (hasStringValue(value = String.valueOf(this.editTextBeschreibung.getText()))) {
            this.projekt.setBeschreibung(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextEnde.getText()))) {
            this.projekt.setEnde(DateUtil.getDateFromString(value));
        }
        if (hasStringValue(value = String.valueOf(this.editTextName.getText()))) {
            this.projekt.setName(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextNummer.getText()))) {
            this.projekt.setNummer(value);
        }
    }

    public interface Callback {
        void onSubmit(Projekt projekt);
    }
}
