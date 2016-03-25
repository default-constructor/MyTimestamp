package de.defaultconstructor.mytimestamp.app.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by thre on 28.02.2016.
 */
public class DatePickerDialogFragment extends DialogFragment {

    public static final String TAG = "fragment_dialog_datepicker";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Calendar calendar = calendar = new GregorianCalendar();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(getArguments().getString("default-date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        this.datePicker = new DatePicker(getActivity());
        this.datePicker.setCalendarViewShown(false);
        this.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), null);
        this.builder = new AlertDialog.Builder(getActivity());
        this.builder.setTitle(getArguments().getString("title"));
        this.builder.setView(this.datePicker);
        this.builder.setPositiveButton("Übernehmen", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int dayOfMonth = DatePickerDialogFragment.this.datePicker.getDayOfMonth();
                int month = DatePickerDialogFragment.this.datePicker.getMonth();
                int year = DatePickerDialogFragment.this.datePicker.getYear();
                Calendar calendar = new GregorianCalendar();
                calendar.set(year, month, dayOfMonth);
                Callback callback = null;
                try {
                    callback = (Callback) getTargetFragment();
                } catch (ClassCastException e) {
                    Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
                    throw e;
                }
                if (null != callback) {
                    callback.onDatePicked(calendar.getTime());
                }
            }
        });
        this.builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO on click negative button
            }
        });
        return this.builder.create();
    }

    private AlertDialog.Builder builder;
    private DatePicker datePicker;

    public static DatePickerDialogFragment newInstance(String title, Date date) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("default-date", new SimpleDateFormat("dd.MM.yyyy").format(date));
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callback {
        void onDatePicked(Object result);
    }
}
