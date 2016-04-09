package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NeuerAuftragFragment extends MyTimestampFragment {

    public static final String TAG = "NeuerAuftragFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_neuerauftrag, container, false);
        initialize();
        setEnableButtonSubmit();
        return this.view;
    }

    private Button buttonSubmit;

    private View view;

    protected void initialize() {
        this.buttonSubmit = (Button) this.view.findViewById(R.id.buttonSubmitNeuerAuftrag);
        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "button submit on click");
            }
        });
    }

    private void setEnableButtonSubmit() {
        this.buttonSubmit.setEnabled(true);
    }
}
