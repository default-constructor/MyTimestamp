package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    public static ProjekteFragment newInstance(String title, boolean titleIconLeft,
                                               boolean titleIconRight, List<Auftrag> auftragList) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putBoolean("titleIconLeft", titleIconLeft);
        bundle.putBoolean("titleIconRight", titleIconRight);
        ProjekteFragment fragment = new ProjekteFragment();
        fragment.auftragList = auftragList;
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ProjekteFragment newInstance(String platzhalterText) {
        Bundle bundle = new Bundle();
        bundle.putString("platzhalterText", platzhalterText);
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
        this.activity = (MainActivity) getActivity();/*
        this.auftragList = this.activity.getAuftragList();*/
        this.bundle = getArguments();
        initialize();
    }

    private MainActivity activity;

    private View view;
    private LinearLayout containerProjekte;
    private RelativeLayout containerTitel;

    private String platzhalterText;
    private String titel;
    private boolean titleIconLeft;
    private boolean titleIconRight;

    private Bundle bundle;

    private List<Auftrag> auftragList = new ArrayList<>();

    private LinearLayout getProjektListView(List<Auftrag> auftragList) {
        LinearLayout listWrapper = new LinearLayout(getActivity());
        listWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        listWrapper.setOrientation(LinearLayout.VERTICAL);
        for (Auftrag auftrag : auftragList) {
            listWrapper.addView(getAuftragListItem(auftrag));
        }
        return listWrapper;
    }

    private ProjektListItemView getAuftragListItem(Auftrag auftrag) {
        ProjektListItemView listItemView = new ProjektListItemView(getActivity());
        listItemView.setPrimaryText(auftrag.getProjekt().getName());
        Projekt projekt = auftrag.getProjekt();
        String secondaryText;
        if (null != projekt.getEnde()) {
            secondaryText = "von " + DateUtil.getDateStringFromDate(projekt.getBeginn()) +
                    " bis " + DateUtil.getDateStringFromDate(projekt.getEnde());
        } else {
            if (Projekt.isAnstehend(projekt)) {
                secondaryText = "ab " + DateUtil.getDateStringFromDate(projekt.getBeginn());
            } else {
                secondaryText = "seit " + DateUtil.getDateStringFromDate(projekt.getBeginn());
            }
        }
        listItemView.setSecondaryText(secondaryText);
        listItemView.setPrimaryTextInfo(auftrag.getAuftraggeber().getFirma());
        return listItemView;/*
        auftragListItem.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_auftraglist_item));*/
    }

    private RelativeLayout.LayoutParams getLayoutParams(int... rules) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (null != rules) {
            for (int rule : rules) {
                layoutParams.addRule(rule);
            }
        }
        return layoutParams;
    }

    private TextView getPlaceholderView() {
        TextView textViewPlaceholder = new TextView(this.activity);
        textViewPlaceholder.setGravity(Gravity.CENTER);
        textViewPlaceholder.setHeight(128);
        textViewPlaceholder.setText(this.platzhalterText);
        textViewPlaceholder.setTextSize(16);
        return textViewPlaceholder;
    }

    private void initialize() {
        this.platzhalterText = this.bundle.getString("platzhalterText");
        this.titel = this.bundle.getString("title");
        this.titleIconLeft = this.bundle.getBoolean("titleIconLeft");
        this.titleIconRight = this.bundle.getBoolean("titleIconRight");
        initializeTitle();
        initializeProjektList();
    }

    private void initializeProjektList() {
        this.containerProjekte = (LinearLayout) this.view
                .findViewById(R.id.linearLayoutFragmentProjekteWrapperProjekte);
        if (this.auftragList.isEmpty()) {
            this.containerProjekte.addView(getPlaceholderView());
        } else {
            this.containerProjekte.addView(getProjektListView(this.auftragList));
        }
    }

    private void initializeTitle() {
        this.containerTitel = (RelativeLayout) this.view.findViewById(R.id.fragmentProjekteTitel);
        if (this.titleIconLeft) {
            ImageView imageViewIconLeft = new ImageView(this.activity);
            imageViewIconLeft.setImageResource(R.drawable.ic_keyboard_arrow_left_black_48dp);
            this.containerTitel.addView(imageViewIconLeft, getLayoutParams(RelativeLayout.ALIGN_PARENT_START));
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textViewTitel = new TextView(this.activity);
        textViewTitel.setLayoutParams(layoutParams);
        textViewTitel.setGravity(Gravity.CENTER);
        textViewTitel.setText(this.titel);
        textViewTitel.setTextSize(16);
        textViewTitel.setTypeface(null, Typeface.BOLD);
        this.containerTitel.addView(textViewTitel);
        if (this.titleIconRight) {
            ImageView imageViewIconRight = new ImageView(this.activity);
            imageViewIconRight.setImageResource(R.drawable.ic_keyboard_arrow_right_black_48dp);
            this.containerTitel.addView(imageViewIconRight, getLayoutParams(RelativeLayout.ALIGN_PARENT_END));
        }
    }
}
