package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import de.thre.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.SettingsActivity;
import de.defaultconstructor.mytimestamp.app.android.widgets.components.AccordionView;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;

/**
 * Created by thre on 28.02.2016.
 */
public class BenutzerdatenFragment extends SettingsFragment implements AccordionView.Listener, SelectionDialogFragment.Callback, DatePickerDialogFragment.Callback {

    public static final String TAG = "fragment_benutzerdaten";

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
    public void onSelected(String result) {
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

    private EditText editTextBerufsstatus;
    private EditText editTextEmail;
    private EditText editTextFamilienname;
    private EditText editTextGeburtsdatum;
    private EditText editTextMobil;
    private EditText editTextPostleitzahl;
    private EditText editTextStraszeUndHausnummer;
    private EditText editTextTelefon;
    private EditText editTextVorname;
    private EditText editTextWohnsitz;

    private Benutzer benutzer;
    private View view;

    public BenutzerdatenFragment() {
        super();
    }

    private void initialize() {
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitBenutzerdaten);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapAdressdaten();
                mapBenutzerdaten();
                mapKontaktdaten();
                try {
                    ((SettingsActivity) getActivity()).onSubmit(BenutzerdatenFragment.this.benutzer);
                } catch (AppException e) {
                    e.printStackTrace();
                }
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
        this.editTextPostleitzahl = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenPostleitzahl);
        this.editTextPostleitzahl.setText(this.benutzer.getAdresse().getPostleitzahl());
        this.editTextPostleitzahl.addTextChangedListener(getTextWatcherForEditText(PATTERN_POSTCODE_TEXT_NO, this.editTextPostleitzahl));

        this.editTextStraszeUndHausnummer = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenStraszeUndHausnummer);
        this.editTextStraszeUndHausnummer.setText(this.benutzer.getAdresse().getStraszeUndHaus());
        this.editTextStraszeUndHausnummer.addTextChangedListener(getTextWatcherForEditText(PATTERN_STREETHOUSENUMBER_TEXT_NO, this.editTextStraszeUndHausnummer));

        this.editTextWohnsitz = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenWohnsitz);
        this.editTextWohnsitz.setText(this.benutzer.getAdresse().getOrtschaft());
        this.editTextWohnsitz.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextWohnsitz));
    }

    private void initializeBenutzerdaten() {
        this.editTextBerufsstatus = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenBerufsstatus);
        this.editTextBerufsstatus.setFocusable(false);
        this.editTextBerufsstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BenutzerdatenFragment", "on click berufsstatus");
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                SelectionDialogFragment dialogFragment = SelectionDialogFragment.newInstance("Berufsstatus auswählen",
                        getResources().getStringArray(R.array.listBerufsstatus));
                dialogFragment.setTargetFragment(BenutzerdatenFragment.this, 1);
                ((SettingsActivity) getActivity()).showDialogFragment(dialogFragment, "selection-berufsstatus");
            }
        });
        this.editTextBerufsstatus.setText("");
        this.editTextBerufsstatus.addTextChangedListener(getTextWatcherForEditText(null, this.editTextBerufsstatus));

        this.editTextFamilienname = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenFamilienname);
        this.editTextFamilienname.setText(this.benutzer.getFamilienname());
        this.editTextFamilienname.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextFamilienname));

        this.editTextGeburtsdatum = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenGeburtsdatum);
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
        this.editTextGeburtsdatum.setText(null != this.benutzer.getGeburtsdatum() ?
                new SimpleDateFormat("dd.MM.yyyy").format(this.benutzer.getGeburtsdatum()) : "");
        this.editTextGeburtsdatum.addTextChangedListener(getTextWatcherForEditText(null, this.editTextGeburtsdatum));

        this.editTextVorname = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenVorname);
        this.editTextVorname.setText(this.benutzer.getVorname());
        this.editTextVorname.addTextChangedListener(getTextWatcherForEditText(PATTERN_NAME_TEXT_NO, this.editTextVorname));
    }

    private void initializeKontaktdaten() {
        this.editTextEmail = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenEmail);
        this.editTextEmail.setText(this.benutzer.getKontakt().getEmail());
        this.editTextEmail.addTextChangedListener(getTextWatcherForEditText(PATTERN_EMAIL_TEXT_NO, this.editTextEmail));

        this.editTextMobil = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenMobil);
        this.editTextMobil.setText(this.benutzer.getKontakt().getMobil());
        this.editTextMobil.addTextChangedListener(getTextWatcherForEditText(PATTERN_PHONE_TEXT_NO, this.editTextMobil));

        this.editTextTelefon = (EditText) this.view.findViewById(R.id.editTextBenutzerdatenTelefon);
        this.editTextTelefon.setText(this.benutzer.getKontakt().getTelefon());
        this.editTextTelefon.addTextChangedListener(getTextWatcherForEditText(PATTERN_PHONE_TEXT_NO, this.editTextTelefon));
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
      //      this.benutzer.setBerufsstatus(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextFamilienname.getText()))) {
            this.benutzer.setFamilienname(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextGeburtsdatum.getText()))) {
            try {
                this.benutzer.setGeburtsdatum(new SimpleDateFormat("dd.MM.yyyy").parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
                // TODO Error-Handling Flush Data Benutzer
            }
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

    private void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(hasStringValue(String.valueOf(this.editTextBerufsstatus.getText()))
                && hasStringValue(String.valueOf(this.editTextFamilienname.getText()))
                && hasStringValue(String.valueOf(this.editTextGeburtsdatum.getText()))
                && hasStringValue(String.valueOf(this.editTextVorname.getText())));
    }
}
