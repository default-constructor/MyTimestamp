package de.defaultconstructor.mytimestamp.app.android.activities;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.NeuerAuftragFragment;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.service.NewMissionService;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionActivity extends MyTimestampActivity implements AuftraggeberdatenFragment.Callback, NeuerAuftragFragment.Callback {

    public static final String TAG = "NewMissionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmission);
        renderFragment(NeuerAuftragFragment.TAG, R.id.activityNewMissionWrapper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.auftraggeberList = this.newMissionService.loadAuftraggeberList();
    }

    @Override
    public void onSubmit(Auftrag auftrag) {
        this.auftrag = auftrag;
        try {
            if (null != this.newMissionService.saveAuftrag(this.auftrag)) {
                finish();
            }
        } catch (ServiceException e) {
            Log.e(TAG, "Save failure: " + e);
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
                renderFragment(NeuerAuftragFragment.TAG, R.id.activityNewMissionWrapper);
                return;
            }
            Log.d(TAG, "on submit Auftraggeber is null");
        } catch (ServiceException e) {
            Log.e(TAG, "Save failure: " + e);
        }
    }

    private NewMissionService newMissionService;

    private Auftrag auftrag = new Auftrag();

    private List<Auftraggeber> auftraggeberList;

    public NewMissionService getNewMissionService() {
        return this.newMissionService;
    }

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

    public Auftraggeber getAuftraggeber(String firma) {
        Log.d(TAG, "get Auftraggeber " + firma);
        for (Auftraggeber auftraggeber : this.auftraggeberList) {
            Log.d(TAG, "check Auftraggeber " + auftraggeber.toString());
            if (auftraggeber.getFirma().equals(firma)) {
                Log.d(TAG, "is Auftraggeber " + auftraggeber.toString());
                return auftraggeber;
            }
        }
        Log.d(TAG, "Auftraggeber not found.");
        return null;
    }
}
