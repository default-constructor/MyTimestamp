package de.defaultconstructor.mytimestamp.app.android.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.fragments.AuftraggeberdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.BenutzerdatenFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.SettingsFragment;
import de.defaultconstructor.mytimestamp.app.exception.AppException;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Person;
import de.defaultconstructor.mytimestamp.app.service.SettingsServiceImpl;

/**
 * Created by Thomas Reno on 27.02.2016.
 */
public class SettingsActivity extends AppCompatActivity implements SettingsFragment.FragmentListener {

    private static final String TAG = "SettingsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MyTimestamp.firstRun) {
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
            if (this.settingsService.saveSettings(this.auftraggeber, this.benutzer)) {
                if (MyTimestamp.firstRun) {
                    MyTimestamp.firstRun = false;
                }
                finish();
            }
        }
    }

    private Benutzer benutzer;
    private Auftraggeber auftraggeber;

    private Map<String, SettingsFragment> mapFragments = new HashMap<>();

    private SettingsServiceImpl settingsService;

    private String tagCurrentFragment;

    public SettingsActivity() {
        super();
        this.settingsService = new SettingsServiceImpl(this);
    }

    public Person getPersonendaten(String tagFragment) {
        Person person;
        switch (tagFragment) {
            case AuftraggeberdatenFragment.TAG:
                person = this.auftraggeber = new Auftraggeber("");
                break;
            case BenutzerdatenFragment.TAG:
                person = this.benutzer = MyTimestamp.currentBenutzer;
                break;
            default:
                person = null;
        }
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

    private void renderFragment(Fragment fragment) {
        if (null == fragment) {
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
