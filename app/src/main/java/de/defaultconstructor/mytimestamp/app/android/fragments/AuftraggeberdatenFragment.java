package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import de.thre.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.SettingsActivity;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;

/**
 * Created by thre on 28.02.2016.
 */
public class AuftraggeberdatenFragment extends SettingsFragment {

    public static final String TAG = "fragment_auftraggeber";

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auftraggeber = (Auftraggeber) ((SettingsActivity) getActivity()).getPersonendaten(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_arbeitgeberdaten, container, false);
        initialize();
        setEnableButtonSubmit();
        return this.view;
    }

    private Button buttonSubmitArbeitgeberdaten;

    private EditText editTextEmail;
    private EditText editTextFirma;
    private EditText editTextFirmensitz;
    private EditText editTextPostleitzahl;
    private EditText editTextStaat;
    private EditText editTextStraszeUndHausnummer;
    private EditText editTextTelefon;
    private EditText editTextWebseite;

    private Auftraggeber auftraggeber;
    private View view;

    public AuftraggeberdatenFragment() {
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
        this.buttonSubmitArbeitgeberdaten = (Button) this.view.findViewById(R.id.buttonSubmitArbeitgeberdaten);
        this.buttonSubmitArbeitgeberdaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapArbeitgeberdaten();
                mapKontaktdaten();
                try {
                    ((SettingsActivity) getActivity()).onSubmit(AuftraggeberdatenFragment.this.auftraggeber);
                } catch (AppException e) {
                    Log.e(TAG, "Beim Speichern der Einstellungen ist ein Fehler aufgetreten.", e);
                }
            }
        });
        initializeAdressdaten();
        initializeAuftaggeberdaten();
        initializeKontaktdaten();
    }

    private void initializeAdressdaten() {
        this.editTextFirmensitz = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenFirmensitz);
        this.editTextFirmensitz.setText(this.auftraggeber.getAdresse().getOrtschaft());
        this.editTextFirmensitz.addTextChangedListener(getTextWatcherForEditText(null, this.editTextFirmensitz));

        this.editTextPostleitzahl = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenPostleitzahl);
        this.editTextPostleitzahl.setText(this.auftraggeber.getAdresse().getPostleitzahl());
        this.editTextPostleitzahl.addTextChangedListener(getTextWatcherForEditText(null, this.editTextPostleitzahl));

        this.editTextStaat = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenStaat);
        this.editTextStaat.setText(this.auftraggeber.getAdresse().getStaat());
        this.editTextStaat.addTextChangedListener(getTextWatcherForEditText(null, this.editTextStaat));

        this.editTextStraszeUndHausnummer = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenStraszeUndHausnummer);
        this.editTextStraszeUndHausnummer.setText(this.auftraggeber.getAdresse().getStraszeUndHaus());
        this.editTextStraszeUndHausnummer.addTextChangedListener(getTextWatcherForEditText(null, this.editTextStraszeUndHausnummer));
    }

    private void initializeAuftaggeberdaten() {
        this.editTextFirma = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenFirma);
        this.editTextFirma.setText(this.auftraggeber.getFirma());
        this.editTextFirma.addTextChangedListener(getTextWatcherForEditText(null, this.editTextFirma));
    }

    private void initializeKontaktdaten() {
        this.editTextEmail = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenEmail);
        this.editTextEmail.setText(this.auftraggeber.getKontakt().getEmail());
        this.editTextEmail.addTextChangedListener(getTextWatcherForEditText(null, this.editTextEmail));

        this.editTextTelefon = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenTelefon);
        this.editTextTelefon.setText(this.auftraggeber.getKontakt().getTelefon());
        this.editTextTelefon.addTextChangedListener(getTextWatcherForEditText(null, this.editTextTelefon));

        this.editTextWebseite = (EditText) this.view.findViewById(R.id.editTextArbeitgeberdatenWebseite);
        this.editTextWebseite.setText(this.auftraggeber.getKontakt().getWebseite());
        this.editTextWebseite.addTextChangedListener(getTextWatcherForEditText(null, this.editTextWebseite));
    }

    private void mapArbeitgeberdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextFirma.getText()))) {
            this.auftraggeber.setFirma(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextFirmensitz.getText()))) {
            this.auftraggeber.getAdresse().setOrtschaft(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextPostleitzahl.getText()))) {
            this.auftraggeber.getAdresse().setPostleitzahl(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextStaat.getText()))) {
            this.auftraggeber.getAdresse().setStaat(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextStraszeUndHausnummer.getText()))) {
            this.auftraggeber.getAdresse().setStraszeUndHaus(value);
        }
    }

    private void mapKontaktdaten() {
        String value;
        if (hasStringValue(value = String.valueOf(this.editTextEmail.getText()))) {
            this.auftraggeber.getKontakt().setEmail(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextTelefon.getText()))) {
            this.auftraggeber.getKontakt().setTelefon(value);
        }
        if (hasStringValue(value = String.valueOf(this.editTextWebseite.getText()))) {
            this.auftraggeber.getKontakt().setWebseite(value);
        }
    }

    private void setEnableButtonSubmit() {
        this.buttonSubmitArbeitgeberdaten.setEnabled(hasStringValue(String.valueOf(this.editTextFirma.getText()))
                && hasStringValue(String.valueOf(this.editTextFirmensitz.getText()))
                && hasStringValue(String.valueOf(this.editTextPostleitzahl.getText()))
                && hasStringValue(String.valueOf(this.editTextStaat.getText()))
                && hasStringValue(String.valueOf(this.editTextStraszeUndHausnummer.getText())));
    }
}
