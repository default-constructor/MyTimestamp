package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.annotation.SuppressLint;
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

import java.util.regex.Pattern;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;

/**
 * Created by Thomas Reno on 28.02.2016.
 */
public class AuftraggeberdatenFragment extends MyTimestampFragment {

    public static final String TAG = "AuftraggeberdatenFragment";

    private static final String TEXT_MESSAGE_ERROR = "Zu besoffen oder was?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auftraggeber = new Auftraggeber();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_auftraggeberdaten, container, false);
        initialize();
        setEnableButtonSubmit();
        return this.view;
    }

    private Button buttonSubmitArbeitgeberdaten;

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextFirma;
    private TextInputEditText editTextFirmensitz;
    private TextInputEditText editTextPostleitzahl;
    private TextInputEditText editTextStaat;
    private TextInputEditText editTextStraszeUndHausnummer;
    private TextInputEditText editTextTelefon;
    private TextInputEditText editTextWebseite;

    private Auftraggeber auftraggeber;
    private View view;

    public AuftraggeberdatenFragment() {
        super();
    }

    protected void initialize() {
        getActivity().setTitle("Neuer Auftraggeber");
        this.buttonSubmitArbeitgeberdaten = (Button) this.view.findViewById(R.id.buttonSubmitArbeitgeberdaten);
        this.buttonSubmitArbeitgeberdaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapArbeitgeberdaten();
                mapKontaktdaten();
                ((NewMissionActivity) getActivity()).onSubmit(AuftraggeberdatenFragment.this.auftraggeber);
            }
        });
        initializeAdressdaten();
        initializeAuftaggeberdaten();
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
        this.editTextFirmensitz = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenFirmensitz);
        this.editTextFirmensitz.addTextChangedListener(getTextWatcherForEditText(null, this.editTextFirmensitz));

        this.editTextPostleitzahl = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenPostleitzahl);
        this.editTextPostleitzahl.addTextChangedListener(getTextWatcherForEditText(null, this.editTextPostleitzahl));

        this.editTextStaat = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenStaat);
        this.editTextStaat.addTextChangedListener(getTextWatcherForEditText(null, this.editTextStaat));

        this.editTextStraszeUndHausnummer = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenStraszeUndHausnummer);
        this.editTextStraszeUndHausnummer.addTextChangedListener(getTextWatcherForEditText(null, this.editTextStraszeUndHausnummer));
    }

    private void initializeAuftaggeberdaten() {
        this.editTextFirma = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenFirma);
        this.editTextFirma.addTextChangedListener(getTextWatcherForEditText(null, this.editTextFirma));
    }

    private void initializeKontaktdaten() {
        this.editTextEmail = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenEmail);
        this.editTextEmail.addTextChangedListener(getTextWatcherForEditText(null, this.editTextEmail));

        this.editTextTelefon = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenTelefon);
        this.editTextTelefon.addTextChangedListener(getTextWatcherForEditText(null, this.editTextTelefon));

        this.editTextWebseite = (TextInputEditText) this.view.findViewById(R.id.editTextArbeitgeberdatenWebseite);
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
        this.buttonSubmitArbeitgeberdaten.setEnabled(hasStringValue(String.valueOf(this.editTextFirma.getText())));
    }

    public interface Callback {
        void onSubmit(Auftraggeber auftraggeber);
    }
}
