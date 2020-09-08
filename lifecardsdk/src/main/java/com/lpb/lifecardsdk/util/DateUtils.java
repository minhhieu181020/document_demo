package com.lpb.lifecardsdk.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lpb.lifecardsdk.constant.Config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    public static final String CALENDAR_DATE_FORMAT_DD_MM_YY_HH = "dd/MM/yyyy HH:mm:ss";
    public static final long INVALID_TIME = -1;

    public static Date stringToDate(String time, String format, Locale locale) {
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, locale);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat(format, locale).parse(time);
            } catch (ParseException e1) {
                Logger.e(DateUtils.class, e1);
                date = new Date();
            }
        }
        return date;
    }

    public static long timeToLong(String time, String format, Locale locale) {
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(format)) {
            return INVALID_TIME;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, locale);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return INVALID_TIME;
        }
        return date.getTime();
    }

    public static String timestampToString(long timestamp, String format, @Nullable Locale locale) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format, locale == null ? Locale.getDefault() : locale);
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }

    public static String changeDateFormat(String oldDate, String oldFormat, String newFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            calendar.setTimeInMillis(simpleDateFormat.parse(oldDate).getTime());
            simpleDateFormat.applyPattern(newFormat);
            return simpleDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static String changeTimeFormat(@NonNull String oldTime, @NonNull String oldFormat,
                                          @NonNull String newFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat, Locale.JAPAN);
        Date date;
        try {
            date = simpleDateFormat.parse(oldTime);
            simpleDateFormat.applyPattern(newFormat);
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date convertToDate(String source, String format) {
        if (source == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

        try {
            return formatter.parse(source);
        } catch (ParseException e) {
            Logger.e(DateUtils.class, e);
        }
        return null;
    }

    public static String convertDateToString(long time, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(new Date(time));
    }

    public static boolean compareTwoDate(String dateBefore, String dateAfter) {
        Date before =
                DateUtils.stringToDate(dateBefore, Config.FORMAT_TIMEZONE_SERVER, Locale.getDefault());

        Date after = DateUtils.stringToDate(dateAfter, Config.FORMAT_TIMEZONE_SERVER, Locale.getDefault());
        return !(before == null || after == null) && before.before(after);
    }

    public static String getCurrentDate() {
        return convertDateToString(System.currentTimeMillis(), Config.FORMAT_DATE_DMY);
    }

    public static boolean compareCurrentDate(long dateCompare, int numberDay) {
        return (System.currentTimeMillis() - dateCompare > TimeUnit.DAYS.toMillis(numberDay));
    }
}
