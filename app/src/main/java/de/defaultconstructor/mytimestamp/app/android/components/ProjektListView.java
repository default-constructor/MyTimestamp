package de.defaultconstructor.mytimestamp.app.android.components;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.defaultconstructor.mytimestamp.app.android.util.FragmentUtil;
import de.defaultconstructor.mytimestamp.app.android.fragments.ProjekteFragment;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Projekt;

/**
 * Created by Thomas Reno on 24.04.2016.
 */
@SuppressWarnings("ResourceType")
public class ProjektListView extends LinearLayout {

    private static final String TAG = "ProjektListView";

    private static int currentPosition = 1;

    public static Projekt currentProjekt;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initialize();
    }

    private FragmentActivity activity;

    private ViewPager viewPager;

    private List<ProjekteFragment> fragmentList = new ArrayList<>();

    private List<List<Auftrag>> auftragListList = new ArrayList<>();
    private List<Auftrag> auftragList;
    private List<String> titelList = new ArrayList<>();

    public void setAuftragList(List<Auftrag> auftragList) {
        this.auftragList = auftragList;
    }

    public ProjektListView(Context context) {
        super(context);
        this.activity = (FragmentActivity) context;
    }

    private void initialize() {
        setId(1);
        initializeProjektLists();
        for (int i = 0; i < this.auftragListList.size(); i++) {
            boolean withIconLeft = (1 < this.auftragListList.size()) && (i != 0);
            boolean withIconRight = (1 < this.auftragListList.size()) && (i != (this.auftragListList.size() - 1));
            if (!this.auftragListList.get(i).isEmpty()) {
                ProjekteFragment fragment = ProjekteFragment.newInstance(this.titelList.get(i), withIconLeft,
                        withIconRight, this.auftragListList.get(i));
                this.fragmentList.add(fragment);
            }
        }
        if (this.fragmentList.isEmpty()) {
            ProjekteFragment fragment = ProjekteFragment.newInstance("Du hast noch keine Projekte.");
            FragmentUtil.setFragment(this.activity, fragment, getId(), false);
        } else if (1 == this.fragmentList.size()) {
            FragmentUtil.setFragment(this.activity, this.fragmentList.get(0), getId(), false);
        } else {
            this.viewPager = new ViewPager(this.activity);
            this.viewPager.setId(2);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            this.viewPager.setLayoutParams(layoutParams);
            Adapter adapter = new Adapter(this.activity.getSupportFragmentManager(), this.fragmentList);
            this.viewPager.setAdapter(adapter);
            addView(this.viewPager);
        }
    }

    private void initializeProjektLists() {
        List<Auftrag> abgeschlosseneProjekte = new ArrayList<>();
        List<Auftrag> anstehendeProjekte = new ArrayList<>();
        List<Auftrag> laufendeProjekte = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            Projekt projekt = auftrag.getProjekt();
            if (Projekt.isAbgeschlossen(projekt)) {
                abgeschlosseneProjekte.add(auftrag);
            } else if (Projekt.isAnstehend(projekt)) {
                anstehendeProjekte.add(auftrag);
            } else {
                laufendeProjekte.add(auftrag);
            }
        }
        if (!abgeschlosseneProjekte.isEmpty()) {
            this.titelList.add("Abgeschlossene Projekte");
            this.auftragListList.add(abgeschlosseneProjekte);
        }
        if (!laufendeProjekte.isEmpty()) {
            this.titelList.add("Laufende Projekte");
            this.auftragListList.add(laufendeProjekte);
        }
        if (!anstehendeProjekte.isEmpty()) {
            this.titelList.add("Anstehende Projekte");
            this.auftragListList.add(anstehendeProjekte);
        }
    }

    private static class Adapter extends FragmentStatePagerAdapter {

        private static List<ProjekteFragment> fragmentList;

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            ProjekteFragment fragment = fragmentList.get(position);
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ProjekteFragment fragment = (ProjekteFragment) super.instantiateItem(container, position);
            this.fragmentMap.put(position, fragment);
            return fragment;
        }

        private Map<Integer, ProjekteFragment> fragmentMap = new HashMap<>();

        public Adapter(FragmentManager fm, List<ProjekteFragment> fragmentList) {
            super(fm);
            Adapter.fragmentList = fragmentList;
        }
    }
}
