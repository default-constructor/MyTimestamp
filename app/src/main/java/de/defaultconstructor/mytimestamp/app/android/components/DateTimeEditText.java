package de.defaultconstructor.mytimestamp.app.android.components;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.defaultconstructor.mytimestamp.app.android.fragments.DatePickerDialogFragment;
import de.defaultconstructor.mytimestamp.app.android.fragments.TimePickerDialogFragment;
import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 21.04.2016.
 */
public class DateTimeEditText extends LinearLayout {

    private static final String TAG = "DateTimeEditText";

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initialize();
    }

    private final Handler handlerDatePickerDialog = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int year = bundle.getInt("year");
            int monthOfYear = bundle.getInt("monthOfYear");
            int dayOfMonth = bundle.getInt("dayOfMonth");
            String dateString = DateUtil.getDateStringFromDate(DateUtil.getDate(year, monthOfYear, dayOfMonth, 0, 0, 0));
            DateTimeEditText.this.editTextDate.setText(dateString);
        }
    };

    private final Handler handlerTimePickerDialog = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int hourOfDay = bundle.getInt("hourOfDay");
            int minute = bundle.getInt("minute");
            String timeString = DateUtil.getTimeStringFromDate(DateUtil.getDate(0, 0, 0, hourOfDay, minute, 0));
            DateTimeEditText.this.editTextTime.setText(timeString);
        }
    };

    private FragmentActivity activity;
    private Fragment fragment;

    private DatePickerDialogFragment datePickerDialogFragment;
    private TimePickerDialogFragment timePickerDialogFragment;

    private TextInputEditText editTextDate;
    private TextInputEditText editTextTime;

    private Map<String, String> mapAttributes = new HashMap<>();

    private boolean withEditTextDate = true;
    private boolean withEditTextTime = true;

    public DateTimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (FragmentActivity) context;
        this.fragment = getFragment(this.activity);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            this.mapAttributes.put(attrs.getAttributeName(i), attrs.getAttributeValue(i));
        }
        setEditTextDate();
        setEditTextTime();
    }

    public EditText getEditTextDate() {
        return this.editTextDate;
    }

    public EditText getEditTextTime() {
        return this.editTextTime;
    }

    public Editable getText() {
        StringBuilder builder = new StringBuilder();
        if (this.withEditTextDate) {
            builder.append(String.valueOf(this.editTextDate.getText()));
        }
        if (this.withEditTextTime) {
            builder.append(" ").append(String.valueOf(this.editTextTime.getText()));
        }
        return Editable.Factory.getInstance().newEditable(builder.toString());
    }

    public void setText(String text) {
        if (this.withEditTextDate) {
            String textDate = DateUtil.getDateStringFromDate(DateUtil.getDateFromString(text));
            this.editTextDate.setText(textDate);
        }
        if (this.withEditTextTime) {
            String textTime = DateUtil.getTimeStringFromDate(DateUtil.getDateFromString(text));
            this.editTextTime.setText(textTime);
        }
    }

    private TextInputEditText getEditText(String hint, OnClickListener onClickListener) {
        TextInputEditText editText = new TextInputEditText(this.activity);
        editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        editText.setFocusable(false);
        editText.setHint(hint);
        editText.setOnClickListener(onClickListener);
        return editText;
    }

    private Fragment getFragment(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    private TextInputLayout getTextInputLayout(TextInputEditText editText) {
        TextInputLayout textInputLayout = new TextInputLayout(this.activity);
        textInputLayout.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textInputLayout.addView(editText);
        return textInputLayout;
    }

    private void initialize() {
        setOrientation(HORIZONTAL);
        if (this.mapAttributes.containsKey("type")) {
            String value = this.mapAttributes.get("type");
            if (!value.contains("date")) {
                this.withEditTextDate = false;
            }
            if (!value.contains("time")) {
                this.withEditTextTime = false;
            }
        }
        if (this.withEditTextDate) {
            addView(getTextInputLayout(this.editTextDate));
        }
        if (this.withEditTextTime) {
            addView(getTextInputLayout(this.editTextTime));
        }
    }

    private void setEditTextDate() {
        this.datePickerDialogFragment = DatePickerDialogFragment.newInstance("Projektbeginn auswählen", new Date(), this.handlerDatePickerDialog);
        this.datePickerDialogFragment.setTargetFragment(this.fragment, 1);
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeEditText.this.datePickerDialogFragment.show(DateTimeEditText.this.activity
                        .getSupportFragmentManager(), "dialog-datepicker-" + getId());
            }
        };
        this.editTextDate = getEditText(this.mapAttributes.get("date_hint"), onClickListener);
    }

    private void setEditTextTime() {
        this.timePickerDialogFragment = TimePickerDialogFragment.newInstance("Uhrzeit auswählen", new Date(), this.handlerTimePickerDialog);
        this.timePickerDialogFragment.setTargetFragment(this.fragment, 2);
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeEditText.this.timePickerDialogFragment.show(DateTimeEditText.this.activity
                        .getSupportFragmentManager(), "dialog-timepicker-" + getId());
            }
        };
        this.editTextTime = getEditText(this.mapAttributes.get("time_hint"), onClickListener);
    }
}
