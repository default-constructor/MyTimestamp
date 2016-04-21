package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Projekt;

/**
 * Created by Thomas Reno on 20.04.2016.
 */
public class ProjekteFragment extends Fragment {

    public static final String TAG = "ProjekteFragment";

    private static final String STATUS_ABGESCHLOSSEN = "abgeschlossen";
    private static final String STATUS_LAUFEND = "laufend";
    private static final String STATUS_ANSTEHEND = "anstehend";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_projekte, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.containerProjekte = (LinearLayout) this.view
                .findViewById(R.id.linearLayoutFragmentProjekteWrapperProjekte);
        Bundle bundle = getArguments();
        if (null != bundle) {
            this.projektstatus = bundle.getString("projektstatus");
            initializeTitle();
            displayProjekte();
        }
    }

    private View view;
    private LinearLayout containerProjekte;

    private String projektstatus;

    private List<Auftrag> auftragList;

    private void displayProjekte() {
        this.auftragList = ((MainActivity) getActivity()).getAuftragList();
        List<Auftrag> displayedProjekte = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            switch (this.projektstatus) {
                case STATUS_ABGESCHLOSSEN:
                    if (isProjektAbgeschlossen(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                case STATUS_ANSTEHEND:
                    if (isProjektAnstehend(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                case STATUS_LAUFEND:
                    if (isProjektLaufend(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                default:
                    //
            }
        }
        if (displayedProjekte.isEmpty()) {
            this.containerProjekte.addView(getPlaceholderView("Du hast keine\n" +
                    this.projektstatus + "en Projekte."));
        } else {
            this.containerProjekte.addView(getAuftragListView(displayedProjekte));
        }
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
        textViewPlaceholder.setGravity(Gravity.CENTER);
        textViewPlaceholder.setHeight(256);
        textViewPlaceholder.setText(text);
        textViewPlaceholder.setTextSize(16);
        return textViewPlaceholder;
    }

    private void initializeTitle() {
        if (!Adapter.PROJEKTSTATUS[0].equals(this.projektstatus)) {
            ImageView imageViewLeftIcon = (ImageView) this.view.findViewById(R.id.imageViewFragmentProjekteTitelIconLeft);
            imageViewLeftIcon.setImageResource(R.drawable.ic_keyboard_arrow_left_black_48dp);
        }
        TextView textView = (TextView) this.view
                .findViewById(R.id.textViewFragmentProjekteTitelText);
        textView.setText(this.projektstatus + "e Projekte");
        if (!Adapter.PROJEKTSTATUS[2].equals(this.projektstatus)) {
            ImageView imageViewRightIcon = (ImageView) this.view.findViewById(R.id.imageViewFragmentProjekteTitelIconRight);
            imageViewRightIcon.setImageResource(R.drawable.ic_keyboard_arrow_right_black_48dp);
        }
    }

    private boolean isProjektAbgeschlossen(Projekt projekt) {
        Log.d(TAG, "is projekt abgeschlossen");
        Date ende = projekt.getEnde();
        Date jetzt = new Date();
        Log.d(TAG, "Ende: " + ende.toString());
        Log.d(TAG, "Jetzt: " + jetzt.toString());
        return (null != ende && jetzt.after(ende));
    }

    private boolean isProjektAnstehend(Projekt projekt) {
        Log.d(TAG, "is projekt anstehend");
        Date beginn = projekt.getBeginn();
        Date jetzt = new Date();
        Log.d(TAG, "Beginn: " + beginn.toString());
        Log.d(TAG, "Jetzt: " + jetzt.toString());
        return jetzt.before(beginn);
    }

    private boolean isProjektLaufend(Projekt projekt) {
        Log.d(TAG, "is projekt laufend");
        Date ende = projekt.getEnde();
        Date jetzt = new Date();
        Log.d(TAG, "Ende: " + ende.toString());
        Log.d(TAG, "Jetzt: " + jetzt.toString());
        return (jetzt.after(projekt.getBeginn()) && (null == ende || jetzt.before(ende)));
    }

    public static class Adapter extends FragmentStatePagerAdapter {

        private static final String[] PROJEKTSTATUS = new String[] {
                "abgeschlossen", "laufend", "anstehend" };

        @Override
        public int getCount() {
            return PROJEKTSTATUS.length;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("projektstatus", PROJEKTSTATUS[position]);
            ProjekteFragment projekteFragment = new ProjekteFragment();
            projekteFragment.setArguments(bundle);
            return projekteFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                String tag = fragment.getTag();
                this.fragmentTagMap.put(position, tag);
            }
            return object;
        }

        private FragmentManager fragmentManager;

        private Map<Integer, String> fragmentTagMap = new HashMap<>();

        public Adapter(FragmentManager fm) {
            super(fm);
            this.fragmentManager = fm;
        }

        public Fragment getFragment(int position) {
            String tag = this.fragmentTagMap.get(position);
            if (null == tag) {
                return null;
            }
            return this.fragmentManager.findFragmentByTag(tag);
        }
    }
}
