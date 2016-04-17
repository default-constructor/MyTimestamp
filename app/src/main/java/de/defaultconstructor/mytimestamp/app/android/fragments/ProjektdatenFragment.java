package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 17.04.2016.
 */
public class ProjektdatenFragment extends MyTimestampFragment implements DatePickerDialogFragment.Callback {

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
        initialize();
        setEnableButtonSubmit();
        return this.view;
    }

    @Override
    public void onDatePicked(String tag, Date result) {
        if (tag.contains("datepicker-projektbeginn")) {
            this.editTextBeginn.setText(DateUtil.getDateStringFromDate(result));
        } else if (tag.contains("datepicker-projektende")) {
            this.editTextEnde.setText(DateUtil.getDateStringFromDate(result));
        }
    }

    @Override
    protected void setEnableButtonSubmit() {
        Log.d(TAG, "set enable button submit");
    }

    private Button buttonSubmit;

    private TextInputEditText editTextBeginn;
    private TextInputEditText editTextBeschreibung;
    private TextInputEditText editTextEnde;
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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ProjektdatenFragment.this.view.getWindowToken(), 0);
                mapProjektdaten();
                ((NewMissionActivity) getActivity()).onSubmit(ProjektdatenFragment.this.projekt);
            }
        });

        this.editTextBeginn = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenBeginn);
        this.editTextBeginn.setFocusable(false);
        this.editTextBeginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Calendar calendar = new GregorianCalendar();
                DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance("Projektbeginn auswählen", calendar.getTime());
                dialogFragment.setTargetFragment(ProjektdatenFragment.this, 2);
                ((NewMissionActivity) getActivity()).showDialogFragment(dialogFragment, "datepicker-projektbeginn");
            }
        });
        this.editTextBeginn.addTextChangedListener(getTextWatcherForEditText(this.editTextBeginn, null, null));

        this.editTextBeschreibung = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenBeschreibung);
        this.editTextBeschreibung.addTextChangedListener(getTextWatcherForEditText(this.editTextBeschreibung, null, null));

        this.editTextEnde = (TextInputEditText) this.view.findViewById(R.id.editTextProjektdatenEnde);
        this.editTextEnde.setFocusable(false);
        this.editTextEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Date ende = DateUtil.getDateFromStringISO8601(String.valueOf(ProjektdatenFragment.this.editTextEnde.getText()));
                if (null == ende) {
                    ende = new GregorianCalendar().getTime();
                }
                DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance("Projektende auswählen", ende);
                dialogFragment.setTargetFragment(ProjektdatenFragment.this, 3);
                ((NewMissionActivity) getActivity()).showDialogFragment(dialogFragment, "datepicker-projektende");
            }
        });
        this.editTextEnde.addTextChangedListener(getTextWatcherForEditText(this.editTextEnde, null, null));

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
