package com.example.android.quakereport.util;

import com.example.android.quakereport.model.Earthquake;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateFormatUtils {

    private static DateFormat shortDateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private static DateFormat timeDateFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());

    private DateFormatUtils() {
    }

    public static String shortStringFromDate(Date date) {
        return shortDateFormatter.format(date);
    }

    public static String shortStringFromEarthquake(Earthquake earthquake) {
        return shortStringFromDate(earthquake.getDate());
    }

    public static String timeStringFromDate(Date date) {
        return timeDateFormatter.format(date);
    }

    public static String timeStringFromEarthquake(Earthquake earthquake) {
        return timeStringFromDate(earthquake.getDate());
    }

}
