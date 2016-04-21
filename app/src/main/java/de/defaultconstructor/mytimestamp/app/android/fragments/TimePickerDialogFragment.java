package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Date;

import de.defaultconstructor.mytimestamp.app.util.DateUtil;

/**
 * Created by Thomas Reno on 21.04.2016.
 */
@SuppressLint("LongLogTag")
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerDialogFragment";

    public static TimePickerDialogFragment newInstance(String title, Date defaultTime, Handler handler) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("default-time", DateUtil.getTimeStringFromDate(defaultTime));
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(bundle);
        fragment.setHandler(handler);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Date date = DateUtil.getTimeFromString(getArguments().getString("default-time"));
        int hour = DateUtil.getHourFromDate(date);
        int minutes = DateUtil.getMinutesFromDate(date);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minutes, DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DialogInterface.BUTTON_NEGATIVE == which) {
                    TimePickerDialogFragment.this.timeSelected = false;
                    dialog.cancel();
                }
            }
        });
        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Übernehmen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DialogInterface.BUTTON_POSITIVE == which) {
                    TimePickerDialogFragment.this.timeSelected = true;
                }
            }
        });
        timePickerDialog.setCancelable(true);
        timePickerDialog.setTitle(getArguments().getString("title"));
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!this.timeSelected) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("hourOfDay", hourOfDay);
        bundle.putInt("minute", minute);
        Message message = new Message();
        message.setData(bundle);
        this.handler.sendMessage(message);
    }

    private Handler handler;

    private boolean timeSelected = false;

    private void setHandler(Handler handler) {
        this.handler = handler;
    }
}
