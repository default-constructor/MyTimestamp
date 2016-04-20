package de.defaultconstructor.mytimestamp.app.android.widgets.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Thomas Reno on 29.02.2016.
 */
public class AccordionView extends LinearLayout {

    private static final String TAG = "AccordionView";

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initializeFlyout();
        initializeCaption();
        handleFlyout();
    }

    private Map<Integer, LinearLayout> itemListView = new HashMap<>();
    private Map<String, String> mapAttributes = new HashMap<>();

    private LinearLayout linearLayoutFlyout;

    private boolean collapsed;

    public AccordionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            this.mapAttributes.put(attrs.getAttributeName(i), attrs.getAttributeValue(i));
        }
    }

    public View getViewById(int resourceId) {
        View view = findViewById(resourceId);
        if (null == view) {
            view = this.linearLayoutFlyout.findViewById(resourceId);
        }
        return view;
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
        textViewCaptionText.setBackgroundColor(Color.parseColor(hasKey ?
                this.mapAttributes.get("caption_background") : "#C6C6C6"));
        textViewCaptionText.setAllCaps(Boolean.valueOf(this.mapAttributes
                .get("caption_textAllCaps")));
        textViewCaptionText.setText(this.mapAttributes.get("caption_text"));
        textViewCaptionText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        textViewCaptionText.setPadding(
                dpToPx(Integer.valueOf(getMatchedString("^\\d+",
                        this.mapAttributes.get("caption_paddingLeft")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+",
                        this.mapAttributes.get("caption_paddingTop")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+",
                        this.mapAttributes.get("caption_paddingRight")))),
                dpToPx(Integer.valueOf(getMatchedString("^\\d+",
                        this.mapAttributes.get("caption_paddingBottom")))));
        textViewCaptionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Integer.valueOf(
                getMatchedString("^\\d+", this.mapAttributes.get("caption_textSize"))));
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
                    Log.e(TAG, "No ScrollView found.");
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
        this.linearLayoutFlyout = new LinearLayout(getContext());
        this.linearLayoutFlyout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.linearLayoutFlyout.setOrientation(VERTICAL);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout textInputLayout = (LinearLayout) getChildAt(0);
            this.itemListView.put(Integer.valueOf(textInputLayout.getId()), textInputLayout);
            ((ViewManager) textInputLayout.getParent()).removeView(textInputLayout);
            this.linearLayoutFlyout.addView(textInputLayout);
        }
        addView(this.linearLayoutFlyout);
        this.collapsed = Boolean.valueOf(this.mapAttributes.get("collapsed"));
    }

    private void handleFlyout() {
        if (!this.collapsed) {
            if (null == this.linearLayoutFlyout.getParent()) {
                addView(this.linearLayoutFlyout);
            }
            this.collapsed = true;
        } else {
            ((ViewManager) this.linearLayoutFlyout.getParent()).removeView(this.linearLayoutFlyout);
            this.collapsed = false;
        }
    }

    public interface Listener {
        void onClickCaption(TextView positionY);
    }
}
