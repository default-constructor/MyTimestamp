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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_benutzerdaten, container, false);
        initialize();
        setEnableButtonSubmit();
        return this.view;
    }

    @Override
    public void onDatePicked(Object result) {
        this.editTextGeburtsdatum.setText(new SimpleDateFormat("dd.MM.yyyy").format((Date) result));
    }

    @Override
    public void onSelected(String tag, String result) {
        this.editTextBerufsstatus.setText(result);
    }

    private static final Pattern PATTERN_STREETHOUSENUMBER_TEXT_NO = Pattern.compile("^[\\w.-]+");
    private static final Pattern PATTERN_EMAIL_TEXT_NO = Pattern.compile("[^°!\"²§³$%&/{([)]=}ß?\\´`€+*~#'<>|µ,;:]");
    private static final Pattern PATTERN_EMAIL_TEXT_YES = Pattern.compile("^[A-Za-z0-9_.-]+@[A-Za-z0-9_-]+.[A-Za-z]{2,11}$");
    private static final Pattern PATTERN_PHONE_TEXT_NO = Pattern.compile("[A-Za-z^°!\"²§³$%&{=}ß?´`€*~#'<>|µ,;:]");
    private static final Pattern PATTERN_POSTCODE_TEXT_NO = Pattern.compile("\\D");
    private static final Pattern PATTERN_NAME_TEXT_NO = Pattern.compile("[\\d°^!\"²§³$%&/([)]=}?\\\\@€+*~#<>|µ,;.:_]");

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

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
                mapAdressdaten();
                mapBenutzerdaten();
                mapKontaktdaten();
                ((SettingsActivity) getActivity()).onSubmit(BenutzerdatenFragment.this.benutzer);
            }
        });
        initializeBenutzerdaten();
        initializeAdressdaten();
        initializeKontaktdaten();
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

    private void initializeAdressdaten() {
        this.editTextPostleitzahl = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenPostleitzahl);
        this.editTextPostleitzahl.addTextChangedListener(getTextWatcherForEditText(PATTERN_POSTCODE_TEXT_NO, this.editTextPostleitzahl));

        this.editTextStraszeUndHausnummer = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenStraszeUndHausnummer);
        this.editTextStraszeUndHausnummer.addTextChangedListener(getTextWatcherForEditText(PATTERN_STREETHOUSENUMBER_TEXT_NO, this.editTextStraszeUndHausnummer));

        this.editTextWohnsitz = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenWohnsitz);
        this.editTextWohnsitz.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextWohnsitz));

        if (!MyTimestamp.firstRun) {
            this.editTextPostleitzahl.setText(this.benutzer.getAdresse().getPostleitzahl());
            this.editTextStraszeUndHausnummer.setText(this.benutzer.getAdresse().getStraszeUndHaus());
            this.editTextWohnsitz.setText(this.benutzer.getAdresse().getOrtschaft());
        }
    }

    private void initializeBenutzerdaten() {
        this.editTextBerufsstatus = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenBerufsstatus);
        this.editTextBerufsstatus.setFocusable(false);
        this.editTextBerufsstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                SelectionDialogFragment dialogFragment = SelectionDialogFragment.newInstance("Berufsstatus auswählen",
                        getResources().getStringArray(R.array.listBerufsstatus));
                dialogFragment.setTargetFragment(BenutzerdatenFragment.this, 1);
                ((SettingsActivity) getActivity()).showDialogFragment(dialogFragment, "selection-berufsstatus");
            }
        });
        this.editTextBerufsstatus.addTextChangedListener(getTextWatcherForEditText(null, this.editTextBerufsstatus));

        this.editTextFamilienname = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenFamilienname);
        this.editTextFamilienname.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextFamilienname));

        this.editTextGeburtsdatum = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenGeburtsdatum);
        this.editTextGeburtsdatum.setFocusable(false);
        this.editTextGeburtsdatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
                DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.newInstance("Geburtsdatum auswählen", calendar.getTime());
                dialogFragment.setTargetFragment(BenutzerdatenFragment.this, 1);
                ((SettingsActivity) getActivity()).showDialogFragment(dialogFragment, "datepicker-geburtsdatum");
            }
        });
        this.editTextGeburtsdatum.addTextChangedListener(getTextWatcherForEditText(null, this.editTextGeburtsdatum));

        this.editTextVorname = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenVorname);
        this.editTextVorname.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextVorname));

        if (!MyTimestamp.firstRun) {
            this.editTextBerufsstatus.setText(this.benutzer.getBerufsstatus().getBezeichnung());
            this.editTextFamilienname.setText(this.benutzer.getFamilienname());
            this.editTextGeburtsdatum.setText(null != this.benutzer.getGeburtsdatum() ?
                    new SimpleDateFormat("dd.MM.yyyy").format(this.benutzer.getGeburtsdatum()) : "");
            this.editTextVorname.setText(this.benutzer.getVorname());
        }
    }

    private void initializeKontaktdaten() {
        this.editTextEmail = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenEmail);
        this.editTextEmail.addTextChangedListener(getTextWatcherForEditText(PATTERN_EMAIL_TEXT_NO, this.editTextEmail));

        this.editTextMobil = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenMobil);
        this.editTextMobil.addTextChangedListener(getTextWatcherForEditText(PATTERN_PHONE_TEXT_NO, this.editTextMobil));

        this.editTextTelefon = (TextInputEditText) this.view.findViewById(R.id.editTextBenutzerdatenTelefon);
        this.editTextTelefon.addTextChangedListener(getTextWatcherForEditText(PATTERN_PHONE_TEXT_NO, this.editTextTelefon));

        if (!MyTimestamp.firstRun) {
            this.editTextEmail.setText(this.benutzer.getKontakt().getEmail());
            this.editTextMobil.setText(this.benutzer.getKontakt().getMobil());
            this.editTextTelefon.setText(this.benutzer.getKontakt().getTelefon());
        }
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

    protected void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(hasStringValue(String.valueOf(this.editTextBerufsstatus.getText()))
                && hasStringValue(String.valueOf(this.editTextFamilienname.getText()))
                && hasStringValue(String.valueOf(this.editTextGeburtsdatum.getText()))
                && hasStringValue(String.valueOf(this.editTextVorname.getText())));
    }

    public interface Callback {

        void onSubmit(Benutzer benutzer);
    }
}
