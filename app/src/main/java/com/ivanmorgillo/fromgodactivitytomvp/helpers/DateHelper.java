package com.ivanmorgillo.fromgodactivitytomvp.helpers;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateHelper {

    public static String prettify(DateTime dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        return formatter.print(dateTime);
    }

    public static String convertDateToString(Context context, DateTime dateTime, Locale locale, DateTimeZone timeZone, String type) {
        String result = "";
        LocalDateTime localDateTime = dateTime.withZone(timeZone).toLocalDateTime();

        DateTimeFormatter dateFormat;
        if (type.equalsIgnoreCase("monthAndDay")) { //e.g. 12 NOV
            java.text.DateFormat df = getMediumDateInstanceWithoutYears(locale);
            result = df.format(localDateTime.toDate()).toUpperCase(locale);
        } else if (type.equalsIgnoreCase("monthAndDayShort")) { //e.g. 11/12
            java.text.DateFormat df = getShortDateInstanceWithoutYears(locale);
            result = df.format(localDateTime.toDate()).toUpperCase(locale);
        } else if (type.equalsIgnoreCase("longdate")) { // e.g. Tuesday, 12/11/2013
            DateTime local_dt = new DateTime(localDateTime.toDate());
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", locale);
            result = sdf.format(localDateTime.toDate()) + ", "
                + DateUtils.formatDateTime(context, local_dt.getMillis(), DateUtils.FORMAT_NUMERIC_DATE);
        } else if (type.equalsIgnoreCase("time")) { // e.g. 11:57
            java.text.DateFormat df = android.text.format.DateFormat.getTimeFormat(context);
            result = df.format(localDateTime.toDate());
        } else if (type.equalsIgnoreCase("timeDate")) { // e.g. 11:57 12/11/2013
            DateTime local_dt = new DateTime(localDateTime.toDate());
            dateFormat = DateTimeFormat.shortTime().withLocale(locale);
            result = local_dt.toString(dateFormat) + " " + DateUtils.formatDateTime(context, local_dt.getMillis(), DateUtils.FORMAT_NUMERIC_DATE);
        } else if (type.equalsIgnoreCase("dayOfWeek")) { // e.g. Wednesday
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", locale);
            result = sdf.format(localDateTime.toDate());
        } else if (type.equalsIgnoreCase("dayOfMonth")) { // e.g. 12
            SimpleDateFormat sdf = new SimpleDateFormat("dd", locale);
            result = sdf.format(localDateTime.toDate());
        }

        return result;
    }

    public static java.text.DateFormat getShortDateInstanceWithoutYears(Locale locale) {
        SimpleDateFormat sdf = (SimpleDateFormat) java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, locale);
        sdf.applyPattern(sdf.toPattern().replaceAll("[^\\p{Alpha}]*y+[^\\p{Alpha}]*", ""));
        return sdf;
    }

    public static java.text.DateFormat getMediumDateInstanceWithoutYears(Locale locale) {
        SimpleDateFormat sdf = (SimpleDateFormat) java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM, locale);
        sdf.applyPattern(sdf.toPattern().replaceAll("([^\\p{Alpha}']|('[\\p{Alpha}]+'))*y+([^\\p{Alpha}']|('[\\p{Alpha}]+'))*", ""));
        return sdf;
    }

}
