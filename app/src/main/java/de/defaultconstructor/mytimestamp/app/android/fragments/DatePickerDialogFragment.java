package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Date;

import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 28.02.2016.
 */
@SuppressLint("LongLogTag")
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "DatePickerDialogFragment";

    public static DatePickerDialogFragment newInstance(String title, Date defaultDate, Handler handler) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("default-date", DateUtil.getDateStringFromDate(defaultDate));
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(bundle);
        fragment.setHandler(handler);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Date date = DateUtil.getDateFromString(getArguments().getString("default-date"));
        int year = DateUtil.getYearFromDate(date);
        int month = DateUtil.getMonthOfYearFromDate(date);
        int dayOfMonth = DateUtil.getDayOfMonthFromDate(date);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DialogInterface.BUTTON_NEGATIVE == which) {
                    DatePickerDialogFragment.this.dateSelected = false;
                    dialog.cancel();
                }
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Übernehmen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DialogInterface.BUTTON_POSITIVE == which) {
                    DatePickerDialogFragment.this.dateSelected = true;
                }
            }
        });
        datePickerDialog.setCancelable(true);
        datePickerDialog.setTitle(getArguments().getString("title"));
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (!this.dateSelected) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("monthOfYear", monthOfYear);
        bundle.putInt("dayOfMonth", dayOfMonth);
        Message message = new Message();
        message.setData(bundle);
        this.handler.sendMessage(message);
    }

    private Handler handler;

    private boolean dateSelected = false;

    private void setHandler(Handler handler) {
        this.handler = handler;
    }
}
