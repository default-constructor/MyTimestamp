package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Projekt;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class MainFragment extends MyTimestampFragment {

    public static final String TAG = "MainFragment";

    public static final long SLEEPTIME = 1000L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeInfoContainer();
        this.auftragList = ((MainActivity) getActivity()).getAuftragList();
        initializeAktuelleAuftraegeContainer();
        initializeAnstehendeAuftraegeContainer();
        initializeAbgeschlosseneAuftraegeContainer();
    }

    @Override
    protected void setEnableButtonSubmit() {
        //
    }

    Thread refreshThread;

    private FloatingActionButton buttonNeuerAuftrag;

    private LinearLayout containerAbgeschlosseneAuftraege;
    private LinearLayout containerAktuelleAuftraege;
    private LinearLayout containerAnstehendeAuftraege;
    private LinearLayout containerInfo;

    private TextView textViewCurrentDate;

    private View view;

    private Map<String, List<Auftrag>> auftragListMap;

    private List<Auftrag> auftragList;
    private List<Auftrag> aktuelleAuftraege;
    private List<Auftrag> anstehendeAuftraege;
    private List<Auftrag> abgeschlosseneAuftraege;

    public MainFragment() {
        super();
    }

    private LinearLayout getAuftragListView(List<Auftrag> auftragList) {
        LinearLayout listWrapper = new LinearLayout(getActivity());
        listWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        listWrapper.setOrientation(LinearLayout.VERTICAL);
        for (Auftrag auftrag : auftragList) {
            listWrapper.addView(getAuftragListItem(auftrag));
        }
        return listWrapper;
    }

    private LinearLayout getAuftragListItem(Auftrag auftrag) {
        TextView textViewFirma = new TextView(getActivity());
        textViewFirma.setGravity(Gravity.CENTER_VERTICAL);
        textViewFirma.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 10));
        textViewFirma.setText(auftrag.getAuftraggeber().getFirma());
        textViewFirma.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        textViewFirma.setTextSize(16);
        LinearLayout auftragListItem = new LinearLayout(getActivity());
        auftragListItem.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 48));
        auftragListItem.setOrientation(LinearLayout.HORIZONTAL);
        auftragListItem.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_auftraglist_item));
        auftragListItem.addView(textViewFirma);
        return auftragListItem;
    }

    private TextView getPlaceholderView(String text) {
        TextView textViewPlaceholder = new TextView(getActivity());
        textViewPlaceholder.setText(text);
        textViewPlaceholder.setTextSize(16);
        textViewPlaceholder.setPadding(0, 16, 0, 16);
        return textViewPlaceholder;
    }

    private void initializeAbgeschlosseneAuftraegeContainer() {
        this.containerAbgeschlosseneAuftraege =
                (LinearLayout) this.view.findViewById(R.id.fragmentMainAbgeschlosseneAuftraegeWrapper);
        this.abgeschlosseneAuftraege = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            Date ende = auftrag.getProjekt().getEnde();
            if (null != ende && new Date().after(ende)) {
                this.abgeschlosseneAuftraege.add(auftrag);
            }
        }
        if (this.abgeschlosseneAuftraege.isEmpty()) {
            this.containerAbgeschlosseneAuftraege.addView(getPlaceholderView("Keine abgeschlossenen Aufträge."));
        } else {
            this.containerAbgeschlosseneAuftraege.addView(getAuftragListView(this.abgeschlosseneAuftraege));
        }
    }

    private void initializeAktuelleAuftraegeContainer() {
        this.containerAktuelleAuftraege =
                (LinearLayout) this.view.findViewById(R.id.fragmentMainAktuelleAuftraegeWrapper);
        this.aktuelleAuftraege = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            Projekt projekt = auftrag.getProjekt();
            Date ende = projekt.getEnde();
            Date jetzt = new Date();
            if (jetzt.after(projekt.getBeginn()) && (null == ende || jetzt.before(ende))) {
                this.aktuelleAuftraege.add(auftrag);
            }
        }
        if (this.aktuelleAuftraege.isEmpty()) {
            this.containerAktuelleAuftraege.addView(getPlaceholderView("Keine aktuellen Aufträge."));
        } else {
            this.containerAktuelleAuftraege.addView(getAuftragListView(this.aktuelleAuftraege));
        }
    }

    private void initializeAnstehendeAuftraegeContainer() {
        this.containerAnstehendeAuftraege =
                (LinearLayout) this.view.findViewById(R.id.fragmentMainAnstehendeAuftraegeWrapper);
        this.anstehendeAuftraege = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            if (new Date().before(auftrag.getProjekt().getBeginn())) {
                this.anstehendeAuftraege.add(auftrag);
            }
        }
        if (this.anstehendeAuftraege.isEmpty()) {
            this.containerAnstehendeAuftraege.addView(getPlaceholderView("Keine anstehenden Aufträge."));
        } else {
            this.containerAnstehendeAuftraege.addView(getAuftragListView(this.anstehendeAuftraege));
        }
    }

    private void initializeInfoContainer() {
        this.textViewCurrentDate = new TextView(getActivity());
        this.textViewCurrentDate.setTypeface(null, Typeface.BOLD);/*
        TextView userName = (TextView) view.findViewById(R.id.textview_user);
        userName.setText(MyTimestamp.currentBenutzer.getVorname() + " " +
                MyTimestamp.currentBenutzer.getFamilienname());*/
        this.containerInfo = (LinearLayout) this.view.findViewById(R.id.fragmentMainInfoWrapper);
        this.containerInfo.addView(this.textViewCurrentDate);
        setTextViewCurrentDate();
        this.buttonNeuerAuftrag = (FloatingActionButton) this.view.findViewById(R.id.buttonNeuerAuftrag);
        this.buttonNeuerAuftrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewMissionActivity.class);
                startActivityForResult(intent, Activity.RESULT_OK);
            }
        });
    }

    private void setTextViewCurrentDate() {
        final Activity activity = getActivity();
        this.refreshThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Date dateTime = new Date();
                            DateFormat dateFormatter = new SimpleDateFormat("EEEE', ' dd. MMMM yyyy", Locale.GERMAN);
                            MainFragment.this.textViewCurrentDate.setText(dateFormatter.format(dateTime));
                        }
                    });
                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e) {
                        // Nothing to do ...
                    }
                }
            }
        });
        this.refreshThread.start();
    }
}
