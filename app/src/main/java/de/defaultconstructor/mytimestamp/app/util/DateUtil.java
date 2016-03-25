package de.defaultconstructor.mytimestamp.app.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by thre on 20.03.2016.
 */
public final class DateUtil {

    public static final String TAG = "DateUtil";

    private DateUtil() {
        //
    }

    /**
     *
     * @param string
     * @return
     */
    public static Date getDateFromString(String string) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Veraendert das Monat des mitgelieferten Datum, um die mitgelieferte Anzahl der Monate.
     * Eine negative Zahl verringert, eine positive Zahl erhoeht das Monat.
     *
     * @param months
     *              int
     * @param date
     *              Date
     *
     * @return date, um months veraendert
     */
    public static Date changeDateInMonths(int months, Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) + months));
        return calendar.getTime();
    }

    /**
     * Veraendert das Jahr des mitgelieferten Datum um die mitgelieferte Anzahl der Jahre.
     * Eine negative Zahl verringert, eine positive Zahl erhoeht das Jahr.
     *
     * @param years
     *              int
     * @param date
     *              Date
     *
     * @return date, um years veraendert
     */
    public static Date changeDateInYears(int years, Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }
}
