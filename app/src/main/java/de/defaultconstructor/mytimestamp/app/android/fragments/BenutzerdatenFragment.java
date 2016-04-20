package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.SettingsActivity;
import de.defaultconstructor.mytimestamp.app.android.widgets.components.AccordionView;
import de.defaultconstructor.mytimestamp.app.enumeration.Berufsstatus;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 28.02.2016.
 */
public class BenutzerdatenFragment extends MyTimestampFragment implements AccordionView.Listener,
        SelectionDialogFragment.Callback, DatePickerDialogFragment.Callback {

    public static final String TAG = "BenutzerdatenFragment";

    private static final Pattern PATTERN_STREETHOUSENUMBER_TEXT_NO = Pattern.compile("^[\\w.-]+");
    private static final Pattern PATTERN_EMAIL_TEXT_NO = Pattern
            .compile("[^°!\"²§³$%&/{([)]=}ß?\\´`€+*~#'<>|µ,;:]");
    private static final Pattern PATTERN_EMAIL_TEXT_YES = Pattern
            .compile("^[A-Za-z0-9_.-]+@[A-Za-z0-9_-]+.[A-Za-z]{2,11}$");
    private static final Pattern PATTERN_PHONE_TEXT_NO = Pattern
            .compile("[A-Za-z^°!\"²§³$%&{=}ß?´`€*~#'<>|µ,;:]");
    private static final Pattern PATTERN_POSTCODE_TEXT_NO = Pattern.compile("\\D");
    private static final Pattern PATTERN_NAME_TEXT_NO = Pattern
            .compile("[\\d°^!\"²§³$%&/([)]=}?\\\\@€+*~#<>|µ,;.:_]");

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

    @Override
    public void onClickCaption(TextView caption) {
        caption.scrollTo(0, 900);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.benutzer = (Benutzer) ((SettingsActivity) getActivity()).getPersonendaten(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_benutzerdaten, container, false);
        return this.view;
    }

    @Override
    public void onDatePicked(String tag, Date result) {
        this.editTextGeburtsdatum.setText(DateUtil.getDateStringFromDate(result));
    }

    @Override
    public void onResume() {
        super.onResume();
        initialize();
        setEnableButtonSubmit();
    }

    @Override
    public void onSelected(String tag, String result) {
        this.editTextBerufsstatus.setText(result);
    }

    @Override
    protected void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(hasStringValue(String.valueOf(this.editTextBerufsstatus
                .getText()))
                && hasStringValue(String.valueOf(this.editTextFamilienname.getText()))
                && hasStringValue(String.valueOf(this.editTextGeburtsdatum.getText()))
                && hasStringValue(String.valueOf(this.editTextVorname.getText())));
    }

    private Button buttonSubmit;

    private TextInputEditText editTextBerufsstatus;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextFamilienname;
    private TextInputEditText editTextGeburtsdatum;
    private TextInputEditText editTextMobil;
    private TextInputEditText editTextPostleitzahl;
    private TextInputEditText editTextStraszeUndHausnummer;
    private TextInputEditText editTextTelefon;
    private TextInputEditText editTextVorname;
    private TextInputEditText editTextWohnsitz;

    private AccordionView accordionViewAdressdaten;
    private AccordionView accordionViewKontaktdaten;

    private Benutzer benutzer;
    private View view;

    public BenutzerdatenFragment() {
        super();
    }

    private void initialize() {
        getActivity().setTitle("Neuer Benutzer");
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitBenutzerdaten);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(BenutzerdatenFragment.this.view.getWindowToken(), 0);
                mapAdressdaten();
                mapBenutzerdaten();
                mapKontaktdaten();
                ((SettingsActivity) getActivity()).onSubmit(BenutzerdatenFragment.this.benutzer);
            }
        });
        this.accordionViewAdressdaten = (AccordionView) this.view
                .findViewById(R.id.fragmentBenutzerdatenAccordionViewAdressdaten);
        this.accordionViewKontaktdaten = (AccordionView) this.view
                .findViewById(R.id.fragmentBenutzerdatenAccordionViewKontaktdaten);
        initializeAdressdaten();
        initializeKontaktdaten();
        initializeBenutzerdaten();
    }

    private void initializeAdressdaten() {
        this.editTextPostleitzahl = (TextInputEditText) this.accordionViewAdressdaten
                .getViewById(R.id.editTextBenutzerdatenPostleitzahl);
        this.editTextPostleitzahl.setText(this.benutzer.getAdresse().getPostleitzahl());
        this.editTextPostleitzahl.addTextChangedListener(getTextWatcherForEditText(
                this.editTextPostleitzahl, PATTERN_POSTCODE_TEXT_NO, TEXT_MESSAGE_ERROR));
        this.editTextStraszeUndHausnummer = (TextInputEditText) this.accordionViewAdressdaten
                .getViewById(R.id.editTextBenutzerdatenStraszeUndHausnummer);
        this.editTextStraszeUndHausnummer.setText(this.benutzer.getAdresse().getStraszeUndHaus());
        this.editTextStraszeUndHausnummer.addTextChangedListener(getTextWatcherForEditText(
                this.editTextStraszeUndHausnummer, PATTERN_STREETHOUSENUMBER_TEXT_NO,
                TEXT_MESSAGE_ERROR));
        this.editTextWohnsitz = (TextInputEditText) this.accordionViewAdressdaten
                .getViewById(R.id.editTextBenutzerdatenWohnsitz);
        this.editTextWohnsitz.setText(this.benutzer.getAdresse().getOrtschaft());
        this.editTextWohnsitz.addTextChangedListener(getTextWatcherForEditText(
                this.editTextWohnsitz, PATTERN_NAME_TEXT_NO, TEXT_MESSAGE_ERROR));
    }

    private void initializeBenutzerdaten() {
        this.editTextBerufsstatus = (TextInputEditText) this.view
                .findViewById(R.id.editTextBenutzerdatenBerufsstatus);
        this.editTextBerufsstatus.setFocusable(false);
        this.editTextBerufsstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                SelectionDialogFragment dialogFragment = SelectionDialogFragment
                        .newInstance("Berufsstatus auswählen",
                                getResources().getStringArray(R.array.listBerufsstatus));
                dialogFragment.setTargetFragment(BenutzerdatenFragment.this, 1);
                ((SettingsActivity) getActivity())
                        .showDialogFragment(dialogFragment, "selection-berufsstatus");
            }
        });
        Berufsstatus berufsstatus = this.benutzer.getBerufsstatus();
        this.editTextBerufsstatus.setText(null != berufsstatus ? berufsstatus.getBezeichnung() : null);
        this.editTextBerufsstatus.addTextChangedListener(getTextWatcherForEditText(
                this.editTextBerufsstatus, null, null));

        this.editTextFamilienname = (TextInputEditText) this.view
                .findViewById(R.id.editTextBenutzerdatenFamilienname);
        this.editTextFamilienname.setText(this.benutzer.getFamilienname());
        this.editTextFamilienname.addTextChangedListener(getTextWatcherForEditText(
                this.editTextFamilienname, PATTERN_NAME_TEXT_NO, TEXT_MESSAGE_ERROR));

        this.editTextGeburtsdatum = (TextInputEditText) this.view
                .findViewById(R.id.editTextBenutzerdatenGeburtsdatum);
        this.editTextGeburtsdatum.setFocusable(false);
        this.editTextGeburtsdatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
                DatePickerDialogFragment dialogFragment = DatePickerDialogFragment
                        .newInstance("Geburtsdatum auswählen", calendar.getTime());
                dialogFragment.setTargetFragment(BenutzerdatenFragment.this, 1);
                ((SettingsActivity) getActivity())
                        .showDialogFragment(dialogFragment, "datepicker-geburtsdatum");
            }
        });
        this.editTextGeburtsdatum.setText(null != this.benutzer.getGeburtsdatum() ?
                new SimpleDateFormat("dd.MM.yyyy").format(this.benutzer
                        .getGeburtsdatum()) : "");
        this.editTextGeburtsdatum.addTextChangedListener(getTextWatcherForEditText(
                this.editTextGeburtsdatum, null, null));

        this.editTextVorname = (TextInputEditText) this.view
                .findViewById(R.id.editTextBenutzerdatenVorname);
        this.editTextVorname.setText(this.benutzer.getVorname());
        this.editTextVorname.addTextChangedListener(getTextWatcherForEditText(
                this.editTextVorname, PATTERN_NAME_TEXT_NO, TEXT_MESSAGE_ERROR));
    }

    private void initializeKontaktdaten() {
        this.editTextEmail = (TextInputEditText) this.accordionViewKontaktdaten
                .getViewById(R.id.editTextBenutzerdatenEmail);
        this.editTextEmail.setText(this.benutzer.getKontakt().getEmail());
        this.editTextEmail.addTextChangedListener(getTextWatcherForEditText(
                this.editTextEmail, PATTERN_EMAIL_TEXT_NO, TEXT_MESSAGE_ERROR));
        this.editTextMobil = (TextInputEditText) this.accordionViewKontaktdaten
                .getViewById(R.id.editTextBenutzerdatenMobil);
        this.editTextMobil.setText(this.benutzer.getKontakt().getMobil());
        this.editTextMobil.addTextChangedListener(getTextWatcherForEditText(
                this.editTextMobil, PATTERN_PHONE_TEXT_NO, TEXT_MESSAGE_ERROR));
        this.editTextTelefon = (TextInputEditText) this.accordionViewKontaktdaten
                .getViewById(R.id.editTextBenutzerdatenTelefon);
        this.editTextTelefon.setText(this.benutzer.getKontakt().getTelefon());
        this.editTextTelefon.addTextChangedListener(getTextWatcherForEditText(
                this.editTextTelefon, PATTERN_PHONE_TEXT_NO, TEXT_MESSAGE_ERROR));
    }

    private void mapAdressdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextPostleitzahl.getText()))) {
            this.benutzer.getAdresse().setPostleitzahl(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextStraszeUndHausnummer.getText()))) {
            this.benutzer.getAdresse().setStraszeUndHaus(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextWohnsitz.getText()))) {
            this.benutzer.getAdresse().setOrtschaft(value);
        }
    }

    private void mapBenutzerdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextBerufsstatus.getText()))) {
            this.benutzer.setBerufsstatus(Berufsstatus.getByBezeichnung(value));
        }
        if (hasStringValue(value = String.valueOf(this.editTextFamilienname.getText()))) {
            this.benutzer.setFamilienname(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextGeburtsdatum.getText()))) {
            this.benutzer.setGeburtsdatum(DateUtil.getDateFromString(value));
        }
        if (hasStringValue(value = String.valueOf(this.editTextVorname.getText()))) {
            this.benutzer.setVorname(value);
        }
    }

    private void mapKontaktdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextEmail.getText()))) {
            this.benutzer.getKontakt().setEmail(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextMobil.getText()))) {
            this.benutzer.getKontakt().setMobil(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextTelefon.getText()))) {
            this.benutzer.getKontakt().setTelefon(value);
        }
    }

    public interface Callback {
        void onSubmit(Benutzer benutzer);
    }
}
