package de.defaultconstructor.mytimestamp.app.android.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import de.defaultconstructor.mytimestamp.app.service.SettingsServiceImpl;
import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.App;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.SettingsFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.BenutzerdatenFragment;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Person;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class SettingsActivity extends AppCompatActivity implements SettingsFragment.FragmentListener {

    private static final String TAG = "SettingsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.firstRun) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        setContentView(R.layout.activity_settings);
        renderFragment(BenutzerdatenFragment.TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSubmit(Person person) throws AppException {
        if (person instanceof Benutzer) {
            this.benutzer = (Benutzer) person;
            renderFragment(AuftraggeberdatenFragment.TAG);
        } else if (person instanceof Auftraggeber) {
            this.auftraggeber = (Auftraggeber) person;
            saveSettings();
            if (App.firstRun) {
                this.preferences.edit().putBoolean("firstRun", false).commit();
            }
            finish();
        }
    }

    private Benutzer benutzer;
    private Auftraggeber auftraggeber;

    private Map<String, SettingsFragment> mapFragments = new HashMap<>();

    private SettingsServiceImpl settingsService;

    private SharedPreferences preferences;

    private String tagCurrentFragment;

    private DatabaseAdapter databaseAdapter;

    public SettingsActivity() {
        super();
        this.databaseAdapter = new DatabaseAdapter(this);
        this.settingsService = new SettingsServiceImpl(this);
    }

    public Person getPersonendaten(String tagFragment) {
        Person person = null;
        this.databaseAdapter.open();
        try {
            switch (tagFragment) {
                case AuftraggeberdatenFragment.TAG:
                    person = this.auftraggeber = ((App) getApplication()).getAuftraggeber();
                    break;
                case BenutzerdatenFragment.TAG:
                    person = this.benutzer = this.settingsService.getCurrentBenutzer();
                    break;
                default:
                    throw new AppException("No such fragment found");
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
        this.databaseAdapter.close();
        return person;
    }

    public void showDialogFragment(DialogFragment dialogFragment, String id) {
        FragmentManager manager = getFragmentManager();
        dialogFragment.show(manager, "dialog-" + id);
    }

    public void renderFragment(String tag) {
        if (!this.mapFragments.containsKey(tag)) {
            this.mapFragments.put(tag, SettingsFragment.newInstance(tag));
        }
        renderFragment(this.mapFragments.get(tag));
    }

    private void saveSettings() throws AppException {
        this.databaseAdapter.open();
        long benutzerId;
        if (App.firstRun) {
            benutzerId = this.databaseAdapter.insert(this.benutzer);
        } else {
            benutzerId = this.databaseAdapter.update(this.benutzer);
        }
        if (0 > benutzerId) {
            throw new AppException("Fehler beim Speichern der Person Benutzer.");
        }
        if (0 > this.databaseAdapter.insert(this.auftraggeber)) {
            throw new AppException("Fehler beim Speichern der Person Auftraggeber.");
        }
        this.databaseAdapter.close();
    }

    private void renderFragment(Fragment fragment) {
        if (null == fragment) {
            Log.e("SettingsActivity", "Fragment is null.");
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activitySettingsWrapper, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.tagCurrentFragment = ((SettingsFragment) fragment).getFragmentTag();
    }
}
