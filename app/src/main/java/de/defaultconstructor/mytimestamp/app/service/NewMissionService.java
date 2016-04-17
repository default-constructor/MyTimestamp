package de.defaultconstructor.mytimestamp.app.service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.exception.PersistenceException;
import de.defaultconstructor.mytimestamp.app.exception.ServiceException;
import de.defaultconstructor.mytimestamp.app.model.Adresse;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Auftraggeber;
import de.defaultconstructor.mytimestamp.app.model.Benutzer;
import de.defaultconstructor.mytimestamp.app.model.Kontakt;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.persistence.DatabaseAdapter;
import de.defaultconstructor.mytimestamp.app.util.DatabaseUtil;

/**
 * Created by Thomas Reno on 09.04.2016.
 */
public class NewMissionService extends MyTimestampService {

    public static final String TAG = "NewMissionService";

    public NewMissionService(Context context) {
        super(context);
        this.databaseAdapter = new DatabaseAdapter(context);
    }

    public List<Auftraggeber> loadAuftraggeberList() throws ServiceException {
        try {
            this.databaseAdapter.open();
            String tableNameAuftraggeber = Auftraggeber.class.getSimpleName().toLowerCase();
            Cursor cursorAuftraggeber = this.databaseAdapter.select(tableNameAuftraggeber,
                    Benutzer.class.getSimpleName().toLowerCase() + "=" + MyTimestamp.currentBenutzer.getId());
            if (null == cursorAuftraggeber || 0 == cursorAuftraggeber.getCount()) {
                return new ArrayList<>();
            }
            List<Auftraggeber> auftraggeberList = new ArrayList<>();
            if (cursorAuftraggeber.moveToFirst()) {
                while (!cursorAuftraggeber.isAfterLast()) {
                    Auftraggeber auftraggeber = (Auftraggeber) DatabaseUtil.mapResult(cursorAuftraggeber, tableNameAuftraggeber);
                    String tableNameAdresse = Adresse.class.getSimpleName().toLowerCase();
                    Cursor cursorAdresse = this.databaseAdapter.select(tableNameAdresse, "id=" +
                            cursorAuftraggeber.getLong(cursorAuftraggeber.getColumnIndex(tableNameAdresse)));
                    if (null == cursorAdresse || 0 == cursorAdresse.getCount()) {
                        throw new ServiceException("Kein Ergebnis für Tabelle " + tableNameAdresse);
                    }
                    if (cursorAdresse.moveToFirst()) {
                        auftraggeber.setAdresse((Adresse) DatabaseUtil.mapResult(cursorAdresse, tableNameAdresse));
                    }
                    String tableNameKontakt = Kontakt.class.getSimpleName().toLowerCase();
                    long idKontakt = cursorAuftraggeber.getLong(cursorAuftraggeber.getColumnIndex(tableNameKontakt));
                    Cursor cursorKontakt = this.databaseAdapter.select(tableNameKontakt, "id=" + idKontakt);
                    if (null == cursorKontakt || 0 == cursorKontakt.getCount()) {
                        throw new ServiceException("Kein Ergebnis für Tabelle " + tableNameKontakt);
                    }
                    if (cursorKontakt.moveToFirst()) {
                        auftraggeber.setKontakt((Kontakt) DatabaseUtil.mapResult(cursorKontakt, tableNameKontakt));
                    }
                    auftraggeberList.add(auftraggeber);
                    cursorAuftraggeber.moveToNext();
                }
            }
            return auftraggeberList;
        } catch (PersistenceException e) {
            return new ArrayList<>();
        } finally {
            this.databaseAdapter.close();
        }
    }

    public List<Projekt> loadProjektList() throws ServiceException {
        try {
            this.databaseAdapter.open();
            String tableNameProjekt = Projekt.class.getSimpleName().toLowerCase();
            Cursor cursorProjekt = this.databaseAdapter.select(tableNameProjekt,
                    Benutzer.class.getSimpleName().toLowerCase() + "=" + MyTimestamp.currentBenutzer.getId());
            if (null == cursorProjekt || 0 == cursorProjekt.getCount()) {
                return new ArrayList<>();
            }
            List<Projekt> projektList = new ArrayList<>();
            while (!cursorProjekt.isAfterLast()) {
                Projekt projekt = (Projekt) DatabaseUtil.mapResult(cursorProjekt, tableNameProjekt);
                String tableNameAdresse = Adresse.class.getSimpleName().toLowerCase();
                Cursor cursorAdresse = this.databaseAdapter.select(tableNameAdresse, "id=" +
                        cursorProjekt.getLong(cursorProjekt.getColumnIndex(tableNameAdresse)));
                if (null == cursorAdresse || 0 == cursorAdresse.getCount()) {
                    throw new ServiceException("Kein Ergebnis für Tabelle " + tableNameAdresse);
                }
                if (cursorAdresse.moveToFirst()) {
                    projekt.setAdresse((Adresse) DatabaseUtil.mapResult(cursorAdresse, tableNameAdresse));
                }
                projektList.add(projekt);
                cursorProjekt.moveToNext();
            }
            return projektList;
        } catch (PersistenceException e) {
            return new ArrayList<>();
        } finally {
            this.databaseAdapter.close();
        }
    }

    public Auftraggeber saveAuftraggeber(Auftraggeber auftraggeber) throws ServiceException {
        try {
            this.databaseAdapter.open();
            auftraggeber.getAdresse().setId(this.databaseAdapter.insert(auftraggeber.getAdresse()).getId());
            auftraggeber.getKontakt().setId(this.databaseAdapter.insert(auftraggeber.getKontakt()).getId());
            auftraggeber.setBenutzer(MyTimestamp.currentBenutzer);
            if (null == this.databaseAdapter.insert(auftraggeber)) {
                throw new ServiceException(Auftraggeber.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return auftraggeber;
        } catch (PersistenceException e) {
            throw new ServiceException(Auftraggeber.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }

    public Auftrag saveAuftrag(Auftrag auftrag) throws ServiceException {
        try {
            this.databaseAdapter.open();
            if (null == this.databaseAdapter.insert(auftrag)) {
                throw new ServiceException(Auftrag.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return auftrag;
        } catch (PersistenceException e) {
            throw new ServiceException(Auftrag.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }

    public Projekt saveProjekt(Projekt projekt) throws ServiceException {
        try {
            this.databaseAdapter.open();
            if (null == this.databaseAdapter.insert(projekt)) {
                throw new ServiceException(Projekt.class.getSimpleName() +
                        " konnte nicht gespeichert werden.");
            }
            return projekt;
        } catch (PersistenceException e) {
            throw new ServiceException(Projekt.class.getSimpleName() +
                    " konnte nicht gespeichert werden.");
        } finally {
            this.databaseAdapter.close();
        }
    }
}
