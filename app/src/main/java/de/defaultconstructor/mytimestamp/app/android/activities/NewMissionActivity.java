package de.defaultconstructor.mytimestamp.app.android.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.MyTimestampFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.NeuerAuftragFragment;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Person;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseEntity;
import de.defaultconstructor.mytimestamp.app.service.NewMissionService;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionActivity extends MyTimestampActivity implements MyTimestampFragment.FragmentListener {

    public static final String TAG = "NewMissionActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        setContentView(R.layout.activity_newmission);
        renderFragment(NeuerAuftragFragment.TAG, R.id.activityNewMissionWrapper);
    }

    @Override
    public void onSubmit(DatabaseEntity databaseEntity) throws AppException {
        Log.d(TAG, "on submit");
        if (databaseEntity instanceof Auftrag) {
            this.auftrag = (Auftrag) databaseEntity;
            if (null != this.newMissionService.saveAuftrag(this.auftrag)) {
                finish();
            }
        } else if (databaseEntity instanceof Auftraggeber) {
            this.auftrag.setAuftraggeber((Auftraggeber) databaseEntity);
            this.auftrag.getAuftraggeber().setBenutzer(MyTimestamp.currentBenutzer);
            Auftraggeber auftraggeber = this.newMissionService.saveAuftraggeber(this.auftrag.getAuftraggeber());
            if (null != auftraggeber) {
                this.auftrag.setAuftraggeber(auftraggeber);
                renderFragment(NeuerAuftragFragment.TAG, R.id.activityNewMissionWrapper);
            }
        }
    }

    private NewMissionService newMissionService;

    private Auftrag auftrag = new Auftrag();

    public NewMissionService getNewMissionService() {
        return this.newMissionService;
    }

    public Auftrag getAuftrag() {
        return this.auftrag;
    }

    public NewMissionActivity() {
        super();
        Log.d(TAG, "new NewMissionActivity");
        this.newMissionService = new NewMissionService(this);
    }

    public String[] getArrayAuftraggeberFirma() {
        List<Auftraggeber> listAuftraggeber = this.newMissionService.loadAuftraggeberList();
        if (listAuftraggeber.isEmpty()) {
            renderFragment(AuftraggeberdatenFragment.TAG, R.id.activityNewMissionWrapper);
            return null;
        }
        String[] arrayAuftraggeberFirma = new String[listAuftraggeber.size()];
        for (int i = 0; i < listAuftraggeber.size(); i++) {
            arrayAuftraggeberFirma[i] = listAuftraggeber.get(i).getFirma();
        }
        return arrayAuftraggeberFirma;
    }
}
