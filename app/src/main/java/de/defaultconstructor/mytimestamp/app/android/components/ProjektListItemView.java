package de.defaultconstructor.mytimestamp.app.android.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Thomas Reno on 21.04.2016.
 */
public class ProjektListItemView extends RelativeLayout {

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initialize();
    }

    private FragmentActivity activity;

    private TextView textViewPrimary;
    private TextView textViewPrimaryInfo;
    private TextView textViewSecondary;
    private TextView textViewSecondaryInfo;

    private String primaryText;
    private String primaryTextInfo;
    private String secondaryText;
    private String secondaryTextInfo;

    public void setPrimaryText(String primaryText) {
        this.primaryText = primaryText;
    }

    public void setPrimaryTextInfo(String primaryTextInfo) {
        this.primaryTextInfo = primaryTextInfo;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public void setSecondaryTextInfo(String secondaryTextInfo) {
        this.secondaryTextInfo = secondaryTextInfo;
    }

    public ProjektListItemView(Context context) {
        super(context);
        this.activity = (FragmentActivity) context;
    }

    private LayoutParams getLayoutParams(int... rules) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int rule : rules) {
            layoutParams.addRule(rule);
        }
        return layoutParams;
    }

    private TextView getTextView(int id, String text, int textSize, int typeface) {
        TextView textView = new TextView(this.activity);
        textView.setId(id);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTypeface(null, typeface);
        return textView;
    }

    private void initialize() {
        setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 80);
        layoutParams.setMargins(0, 8, 0, 0);
        setLayoutParams(layoutParams);
        setPadding(16, 8, 16, 8);

        this.textViewPrimary = getTextView(1, this.primaryText, 16, Typeface.BOLD);
        this.textViewPrimaryInfo = getTextView(2, this.primaryTextInfo, 12, Typeface.NORMAL);
        this.textViewSecondary = getTextView(3, this.secondaryText, 12, Typeface.NORMAL);
        this.textViewSecondaryInfo = getTextView(4, this.secondaryTextInfo, 12, Typeface.NORMAL);

        addView(this.textViewPrimary, getLayoutParams(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_LEFT));
        addView(this.textViewPrimaryInfo, getLayoutParams(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT));
        addView(this.textViewSecondary, getLayoutParams(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.ALIGN_PARENT_LEFT));
        addView(this.textViewSecondaryInfo, getLayoutParams(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.ALIGN_PARENT_RIGHT));
    }
}
