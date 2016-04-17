package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class MainFragment extends MyTimestampFragment {

    public static final String TAG = "MainFragment";

    public static final long SLEEPTIME = 1000L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "on create view");
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "on resume");
        this.textViewCurrentDate = new TextView(getActivity());
        this.textViewCurrentDate.setTypeface(null, Typeface.BOLD);/*
        TextView userName = (TextView) view.findViewById(R.id.textview_user);
        userName.setText(MyTimestamp.currentBenutzer.getVorname() + " " +
                MyTimestamp.currentBenutzer.getFamilienname());*/
        this.containerInfoWrapper = (LinearLayout) this.view.findViewById(R.id.fragmentMainInfoWrapper);
        this.containerInfoWrapper.addView(this.textViewCurrentDate);
        setTextViewCurrentDate();
        this.buttonNeuerAuftrag = (FloatingActionButton) this.view.findViewById(R.id.buttonNeuerAuftrag);
        this.buttonNeuerAuftrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewMissionActivity.class);
                startActivityForResult(intent, Activity.RESULT_OK);
            }
        });
        this.containerAuftragWrapper =
                (LinearLayout) this.view.findViewById(R.id.fragmentMainAuftragWrapper);
        this.auftragList = ((MainActivity) getActivity()).getAuftragList();
        if (null == this.auftragList || this.auftragList.isEmpty()) {
            this.containerAuftragWrapper.addView(getPlaceholderView());
        } else {
            this.containerAuftragWrapper.addView(getAuftragListView());
        }
    }

    @Override
    protected void setEnableButtonSubmit() {
        //
    }

    Thread refreshThread;

    private FloatingActionButton buttonNeuerAuftrag;

    private LinearLayout containerAuftragWrapper;
    private LinearLayout containerInfoWrapper;

    private TextView textViewCurrentDate;

    private View view;

    private List<Auftrag> auftragList;

    public MainFragment() {
        super();
    }

    private LinearLayout getAuftragListView() {
        LinearLayout listWrapper = new LinearLayout(getActivity());
        listWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        listWrapper.setOrientation(LinearLayout.VERTICAL);
        for (Auftrag auftrag : this.auftragList) {
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

    private TextView getPlaceholderView() {
        TextView textViewPlaceholder = new TextView(getActivity());
        textViewPlaceholder.setTypeface(null, Typeface.BOLD);
        textViewPlaceholder.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewPlaceholder.setText("Du hast aktuell keine Aufträge.");
        textViewPlaceholder.setTextSize(16);
        textViewPlaceholder.setPadding(0, 16, 0, 16);
        return textViewPlaceholder;
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
