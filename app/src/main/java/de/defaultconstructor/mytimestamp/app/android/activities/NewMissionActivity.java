package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftragsdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.ProjektdatenFragment;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.service.NewMissionService;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionActivity extends MyTimestampActivity implements AuftraggeberdatenFragment.Callback, AuftragsdatenFragment.Callback, ProjektdatenFragment.Callback {

    public static final String TAG = "NewMissionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        this.view = super.onCreateView(name, context, attrs);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_newmission);
        try {
            this.auftraggeberList = this.newMissionService.loadAuftraggeberList();
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            renderFragment(AuftragsdatenFragment.TAG, R.id.activityNewMissionWrapper, true);
        } catch (AndroidException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onSubmit(Auftrag auftrag) {
        this.auftrag = auftrag;
        try {
            renderFragment(ProjektdatenFragment.TAG, R.id.activityNewMissionWrapper, true);
        } catch (AndroidException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onSubmit(Auftraggeber auftraggeber) {
        this.auftrag.setAuftraggeber(auftraggeber);
        this.auftrag.getAuftraggeber().setBenutzer(MyTimestamp.currentBenutzer);
        try {
            if (null != this.newMissionService.saveAuftraggeber(this.auftrag.getAuftraggeber())) {
                this.auftrag.setAuftraggeber(auftraggeber);
                this.auftraggeberList.add(auftraggeber);
                renderFragment(AuftragsdatenFragment.TAG, R.id.activityNewMissionWrapper, false);
            }
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        } catch (AndroidException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onSubmit(Projekt projekt) {
        this.auftrag.setProjekt(projekt);
        try {
            if (null != this.newMissionService.saveAuftrag(this.auftrag)) {
                finish();
            }
        } catch (ServiceException e) {
            Log.e(TAG, "Save failure: " + e);
        }
    }

    private NewMissionService newMissionService;

    private Auftrag auftrag = new Auftrag();

    private List<Auftraggeber> auftraggeberList;
    private List<Projekt> projektList;

    private View view;

    public Auftrag getAuftrag() {
        return this.auftrag;
    }

    public NewMissionActivity() {
        super();
        this.newMissionService = new NewMissionService(this);
    }

    public String[] getArrayAuftraggeberFirma() {
        String[] arrayAuftraggeberFirma = new String[this.auftraggeberList.size()];
        for (int i = 0; i < this.auftraggeberList.size(); i++) {
            arrayAuftraggeberFirma[i] = this.auftraggeberList.get(i).getFirma();
        }
        return arrayAuftraggeberFirma;
    }

    public String[] getArrayProjektName() {
        String[] arrayProjektName = new String[this.projektList.size()];
        for (int i = 0; i < this.projektList.size(); i++) {
            arrayProjektName[i] = this.projektList.get(i).getName();
        }
        return arrayProjektName;
    }

    public Auftraggeber getAuftraggeber(String firma) {
        for (Auftraggeber auftraggeber : this.auftraggeberList) {
            if (auftraggeber.getFirma().equals(firma)) {
                return auftraggeber;
            }
        }
        return null;
    }
}
