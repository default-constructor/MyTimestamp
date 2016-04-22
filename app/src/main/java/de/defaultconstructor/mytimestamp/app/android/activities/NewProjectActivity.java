package de.defaultconstructor.mytimestamp.app.android.activities;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftragsdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.MainFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.ProjektdatenFragment;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.service.NewProjectService;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewProjectActivity extends MyTimestampActivity implements AuftraggeberdatenFragment.Callback, AuftragsdatenFragment.Callback, ProjektdatenFragment.Callback {

    public static final String TAG = "NewProjectActivity";

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        this.view = super.onCreateView(name, context, attrs);
        return this.view;
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
            renderFragment(ProjektdatenFragment.TAG, R.id.activityNewMissionWrapper, true, true);
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
                renderFragment(AuftragsdatenFragment.TAG, R.id.activityNewMissionWrapper, true, true);
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
                MainFragment.currentProjekt = this.auftrag.getProjekt();
                finish();
            }
        } catch (ServiceException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private NewProjectService newMissionService;

    private Auftrag auftrag = new Auftrag();

    private List<Auftraggeber> auftraggeberList;

    private View view;

    public Auftrag getAuftrag() {
        return this.auftrag;
    }

    public NewProjectActivity() {
        super();
        this.newMissionService = new NewProjectService(this);
    }

    public String[] getArrayAuftraggeberFirma() {
        String[] arrayAuftraggeberFirma = new String[this.auftraggeberList.size()];
        for (int i = 0; i < this.auftraggeberList.size(); i++) {
            arrayAuftraggeberFirma[i] = this.auftraggeberList.get(i).getFirma();
        }
        return arrayAuftraggeberFirma;
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
