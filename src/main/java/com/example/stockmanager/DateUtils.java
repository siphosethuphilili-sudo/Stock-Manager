package com.example.stockmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String formatDate(long timestamp) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.setTimeInMillis(timestamp);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (isSameDay(now, then)) {
            return "Today, " + timeFormat.format(new Date(timestamp));
        }
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        if (isSameDay(yesterday, then)) {
            return "Yesterday, " + timeFormat.format(new Date(timestamp));
        }
        SimpleDateFormat fullFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        return fullFormat.format(new Date(timestamp));
    }

    private static boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public static long startOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long startOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
