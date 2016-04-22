package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.defaultconstructor.mytimestamp.R;
import de.defaultconstructor.mytimestamp.app.android.activities.MainActivity;
import de.defaultconstructor.mytimestamp.app.android.components.ProjektListItemView;
import de.defaultconstructor.mytimestamp.app.model.Auftrag;
import de.defaultconstructor.mytimestamp.app.model.Projekt;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 20.04.2016.
 */
public class ProjekteFragment extends Fragment {

    public static final String TAG = "ProjekteFragment";

    private static final String PROJEKTSTATUS_ABGESCHLOSSEN = "abgeschlossen";
    private static final String PROJEKTSTATUS_LAUFEND = "laufend";
    private static final String PROJEKTSTATUS_ANSTEHEND = "anstehend";

    public static ProjekteFragment newInstance(String title, String projektstatus, String platzhalterText) {
        Bundle bundle = new Bundle();
        bundle.putString("titel", title);
        bundle.putString("platzhalterText", platzhalterText);
        bundle.putString("projektstatus", projektstatus);
        ProjekteFragment fragment = new ProjekteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_projekte, container, false);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.activity = (MainActivity) getActivity();
        this.auftragList = this.activity.getAuftragList();
        this.bundle = getArguments();
        initialize();
    }

    private MainActivity activity;

    private View view;
    private LinearLayout containerProjekte;

    private String platzhalterText;
    private String projektstatus;
    private String titel;

    private Bundle bundle;

    private List<Auftrag> auftragList;

    private void displayProjekte() {
        List<Auftrag> displayedProjekte = new ArrayList<>();
        for (Auftrag auftrag : this.auftragList) {
            switch (this.projektstatus) {
                case PROJEKTSTATUS_ABGESCHLOSSEN:
                    if (Projekt.isAbgeschlossen(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                case PROJEKTSTATUS_ANSTEHEND:
                    if (Projekt.isAnstehend(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                case PROJEKTSTATUS_LAUFEND:
                    if (Projekt.isLaufend(auftrag.getProjekt())) {
                        displayedProjekte.add(auftrag);
                    }
                    break;
                default:
                    //
            }
        }
        if (displayedProjekte.isEmpty()) {
            this.containerProjekte.addView(getPlaceholderView());
        } else {
            this.containerProjekte.addView(getProjektListView(displayedProjekte));
        }
    }

    private LinearLayout getProjektListView(List<Auftrag> auftragList) {
        LinearLayout listWrapper = new LinearLayout(getActivity());
        listWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        listWrapper.setOrientation(LinearLayout.VERTICAL);
        listWrapper.setPadding(8, 8, 8, 8);
        for (Auftrag auftrag : auftragList) {
            listWrapper.addView(getAuftragListItem(auftrag));
        }
        return listWrapper;
    }

    private ProjektListItemView getAuftragListItem(Auftrag auftrag) {
        ProjektListItemView listItemView = new ProjektListItemView(getActivity());
        listItemView.setPrimaryText(auftrag.getProjekt().getName());
        String secondaryText;
        if (null != auftrag.getProjekt().getEnde()) {
            secondaryText = "von " + DateUtil.getDateStringFromDate(auftrag.getProjekt().getBeginn()) +
                    " bis " + DateUtil.getDateStringFromDate(auftrag.getProjekt().getEnde());
        } else {
            secondaryText = "ab " + DateUtil.getDateStringFromDate(auftrag.getProjekt().getBeginn());
        }
        listItemView.setSecondaryText(secondaryText);
        listItemView.setPrimaryTextInfo(auftrag.getAuftraggeber().getFirma());
        return listItemView;/*
        auftragListItem.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_auftraglist_item));*/
    }

    private TextView getPlaceholderView() {
        TextView textViewPlaceholder = new TextView(getActivity());
        textViewPlaceholder.setGravity(Gravity.CENTER);
        textViewPlaceholder.setHeight(256);
        textViewPlaceholder.setText(this.platzhalterText);
        textViewPlaceholder.setTextSize(16);
        return textViewPlaceholder;
    }

    private void initialize() {
        this.platzhalterText = this.bundle.getString("platzhalterText");
        this.projektstatus = this.bundle.getString("projektstatus");
        this.titel = this.bundle.getString("titel");
        this.containerProjekte = (LinearLayout) this.view.findViewById(R.id.linearLayoutFragmentProjekteWrapperProjekte);
        initializeTitle();
        displayProjekte();
    }

    private void initializeTitle() {
        if (!PROJEKTSTATUS_ABGESCHLOSSEN.equals(this.projektstatus)) {
            ImageView imageViewLeftIcon = (ImageView) this.view.findViewById(R.id.imageViewFragmentProjekteTitelIconLeft);
            imageViewLeftIcon.setImageResource(R.drawable.ic_keyboard_arrow_left_black_48dp);
        }
        TextView textView = (TextView) this.view.findViewById(R.id.textViewFragmentProjekteTitelText);
        textView.setText(this.titel);
        if (!PROJEKTSTATUS_ANSTEHEND.equals(this.projektstatus)) {
            ImageView imageViewRightIcon = (ImageView) this.view.findViewById(R.id.imageViewFragmentProjekteTitelIconRight);
            imageViewRightIcon.setImageResource(R.drawable.ic_keyboard_arrow_right_black_48dp);
        }
    }
}
