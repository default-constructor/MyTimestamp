package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.MyTimestamp;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.android.activities.NewMissionActivity;
import de.defaultconstructor.mytimestamp.app.exception.AndroidException;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;

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
        this.buttonNeuerAuftrag = (Button) this.view.findViewById(R.id.buttonNeuerAuftrag);
        this.buttonNeuerAuftrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewMissionActivity.class);
                startActivityForResult(intent, Activity.RESULT_OK);
            }
        });
        TextView userName = (TextView) view.findViewById(R.id.textview_user);
        userName.setText(MyTimestamp.currentBenutzer.getVorname() + " " + MyTimestamp.currentBenutzer.getFamilienname());

        this.containerAuftragWrapper = (LinearLayout) this.view.findViewById(R.id.fragmentMainAuftragWrapper);
        List<Auftrag> auftragList = ((MainActivity) getActivity()).getAuftragList();
        if (auftragList.isEmpty()) {
            TextView textView = new TextView(getActivity());
            textView.setText("Sie haben aktuell keine Aufträge.");
        }
    }

    Thread refreshThread;

    private Button buttonNeuerAuftrag;

    private LinearLayout containerAuftragWrapper;

    private TextView textViewCurrentDate;

    private View view;

    public MainFragment() {
        super();
    }

    private void setTextViewCurrentDate() {
        this.textViewCurrentDate = (TextView) view.findViewById(R.id.textview_currentdate);
        this.refreshThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainFragment.this.textViewCurrentDate.setText(getCurrentDateString());
                        }
                    });
                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException e) {
                        // Nothing to do...
                    }
                }
            }
        });
        this.refreshThread.start();
    }

    private String getCurrentDateString() {
        Calendar calendar = Calendar.getInstance();
        Date dateTime = calendar.getTime();
        DateFormat dateFormatter = new SimpleDateFormat("EEEE', ' dd. MMMM yyyy", Locale.GERMAN);
        return dateFormatter.format(dateTime);
    }
}
