package de.defaultconstructor.mytimestamp.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public final class DateUtil {

    public static final String TAG = "DateUtil";

    private DateUtil() {
        //
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

    /**
     *
     * @param string
     * @return
     */
    public static Date getDateFromString(String string) {
        if (null == string) {
            return null;
        }
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateStringFromDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    public static String getStringFromDateISO8601(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return formatter.format(date);
    }
}
