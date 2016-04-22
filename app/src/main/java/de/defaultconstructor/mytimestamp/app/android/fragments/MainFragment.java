package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.android.activities.NewProjectActivity;
import de.defaultconstructor.mytimestamp.app.model.Projekt;

/**
 * Created by Thomas Reno on 10.04.2016.
 */
public class MainFragment extends MyTimestampFragment {

    public static final String TAG = "MainFragment";

    public static final long SLEEPTIME = 1000L;

    private static int currentPosition = 1;

    public static Projekt currentProjekt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.activity = (MainActivity) getActivity();
        initializeInfoContainer();
        this.adapter = new Adapter(this.activity.getSupportFragmentManager());
        this.viewPager = (ViewPager) this.view.findViewById(R.id.viewPagerFragmentMain);
        this.viewPager.setAdapter(this.adapter);
        MainFragment.currentPosition = getCurrentPosition();
        this.viewPager.setCurrentItem(MainFragment.currentPosition);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }
            @Override
            public void onPageSelected(int position) {
                MainFragment.currentPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });
    }

    private int getCurrentPosition() {
        if (null == MainFragment.currentProjekt) {
            return MainFragment.currentPosition;
        } else {
            if (Projekt.isAbgeschlossen(MainFragment.currentProjekt)) {
                return 0;
            }
            if (Projekt.isAnstehend(MainFragment.currentProjekt)) {
                return 2;
            }
            MainFragment.currentProjekt = null;
            return 1;
        }
    }

    @Override
    protected void setEnableButtonSubmit() {
        //
    }

    Thread refreshThread;

    private FloatingActionButton buttonNeuerAuftrag;

    private LinearLayout containerInfo;

    private TextView textViewCurrentDate;

    private MainActivity activity;
    private View view;
    private ViewPager viewPager;
    private Adapter adapter;

    public MainFragment() {
        super();
    }

    private void initializeInfoContainer() {
        this.textViewCurrentDate = new TextView(this.activity);
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
                Intent intent = new Intent(MainFragment.this.activity, NewProjectActivity.class);
                startActivityForResult(intent, Activity.RESULT_OK);
            }
        });
    }

    private void setTextViewCurrentDate() {
        this.refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    MainFragment.this.activity.runOnUiThread(new Runnable() {
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

    private static class Adapter extends FragmentStatePagerAdapter {

        private static final String[] PROJEKTSTATUS = new String[] {
                "abgeschlossen", "laufend", "anstehend" };

        @Override
        public int getCount() {
            return PROJEKTSTATUS.length;
        }

        @Override
        public Fragment getItem(int position) {
            ProjekteFragment fragment = this.fragmentMap.get(position);
            if (null == fragment) {
                return ProjekteFragment
                        .newInstance(PROJEKTSTATUS[position] + "e Projekte", PROJEKTSTATUS[position],
                                "Du hast keine\n" + PROJEKTSTATUS[position] + "en Projekte.");
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ProjekteFragment fragment = (ProjekteFragment) super.instantiateItem(container, position);
            this.fragmentMap.put(position, fragment);
            return fragment;
        }

        private Map<Integer, ProjekteFragment> fragmentMap = new HashMap<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }
    }
}
