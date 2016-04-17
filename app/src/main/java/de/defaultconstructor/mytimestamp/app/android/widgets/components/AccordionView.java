package de.defaultconstructor.mytimestamp.app.android.widgets.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Thomas Reno on 29.02.2016.
 */
public class AccordionView extends LinearLayout {

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initializeFlyout();
        initializeCaption();
        handleFlyout();
    }

    private List<LinearLayout> itemListView;
    private Map<String, String> mapAttributes = new HashMap<>();

    private LinearLayout linearLayoutFlyout;

    private boolean isCollapsed;

    public AccordionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            this.mapAttributes.put(attrs.getAttributeName(i), attrs.getAttributeValue(i));
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private String getMatchedString(String regex, String value) {
        String matchedString = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            matchedString = matcher.group(0);
        }
        return matchedString;
    }

    private void initializeCaption() {
        setOrientation(VERTICAL);
        setPadding(0, 0, 0, 16);

        final TextView textViewCaptionText = new TextView(getContext());
        addView(textViewCaptionText, 0);
        boolean hasKey = this.mapAttributes.containsKey("caption_background");
        textViewCaptionText.setBackgroundColor(Color.parseColor(hasKey ? this.mapAttributes.get("caption_background") : "#C6C6C6"));
        textViewCaptionText.setAllCaps(Boolean.valueOf(this.mapAttributes.get("caption_textAllCaps")));
        textViewCaptionText.setText(this.mapAttributes.get("caption_text"));
        textViewCaptionText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        textViewCaptionText.setPadding(
                dpToPx(Integer.valueOf(getMatchedString("^\\d+", this.mapAttributes.get("caption_paddingLeft")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+", this.mapAttributes.get("caption_paddingTop")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+", this.mapAttributes.get("caption_paddingRight")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+", this.mapAttributes.get("caption_paddingBottom")))));
        textViewCaptionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Integer.valueOf(getMatchedString("^\\d+", this.mapAttributes.get("caption_textSize"))));
        textViewCaptionText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFlyout();
                boolean hasScrollView = false;
                View view;
                try {
                    view = (View) getParent();
                    while (!hasScrollView) {
                        if (view instanceof ScrollView) {
                            hasScrollView = true;
                        } else {
                            view = (View) view.getParent();
                        }
                    }
                } catch (Exception e) {
                    Log.e("AccordionView", "No ScrollView found.");
                    return;
                }

                final ScrollView scrollView = (ScrollView) view;
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, ((int) AccordionView.this.getY()) - dpToPx(8));
                    }
                });
            }
        });
    }

    private void initializeFlyout() {
        int childCount = getChildCount();
        if (0 == childCount) {
            return;
        }

        this.itemListView = new ArrayList<>();
        this.linearLayoutFlyout = new LinearLayout(getContext());
        this.linearLayoutFlyout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.linearLayoutFlyout.setOrientation(VERTICAL);

        for (int i = 0; i < childCount; i++) {
            LinearLayout textInputLayout = (LinearLayout) getChildAt(0);
            ((ViewManager) textInputLayout.getParent()).removeView(textInputLayout);
            this.itemListView.add(textInputLayout);
            this.linearLayoutFlyout.addView(this.itemListView.get(i));
        }

        addView(this.linearLayoutFlyout);
        this.isCollapsed = Boolean.valueOf(this.mapAttributes.get("collapsed"));
    }

    private void handleFlyout() {
        if (!this.isCollapsed) {
            if (null == this.linearLayoutFlyout.getParent()) {
                addView(this.linearLayoutFlyout);
            }
            this.isCollapsed = true;
        } else {
            ((ViewManager) this.linearLayoutFlyout.getParent()).removeView(this.linearLayoutFlyout);
            this.isCollapsed = false;
        }
    }

    public interface Listener {
        void onClickCaption(TextView positionY);
    }
}
