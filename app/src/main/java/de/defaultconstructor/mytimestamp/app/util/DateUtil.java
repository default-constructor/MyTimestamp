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

    private static final SimpleDateFormat FORMATTER_ISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat FORMATTER_TIME = new SimpleDateFormat("hh:mm:ss");

    private DateUtil() {
        //
    }

    /**
     *
     * @param date
     * @param days
     * @return
     */
    public static Date changeDateInDays(int days, Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
        return calendar.getTime();
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
        if (null == date) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);
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
        if (null == date) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);
        return calendar.getTime();
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
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

    public static Date getDateFromStringISO8601(String string) {
        try {
            return FORMATTER_ISO8601.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateStringFromDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return formatter.format(date);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static int getDayOfMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHourFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutesFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getMonthOfYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static String getStringFromDateISO8601(Date date) {
        try {
            return FORMATTER_ISO8601.format(date);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static Date getTimeFromString(String string) {
        try {
            return FORMATTER_TIME.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getTimeStringFromDate(Date date) {
        try {
            return FORMATTER_TIME.format(date);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
