package net.lldv.pydow.core.components.tools;

import net.lldv.pydow.core.components.language.Language;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimeTool {

    public static ArrayList<String> timeStrings = new ArrayList<>();

    public void init() {
        timeStrings.add("weeks");
        timeStrings.add("days");
        timeStrings.add("hours");
        timeStrings.add("minutes");
    }

    public static long weeksInMillis(double weeks) {
        return (long) (weeks * 604800000);
    }

    public static long daysInMillis(double days) {
        return (long) (days * 86400000);
    }

    public static long hoursInMillis(double hours) {
        return (long) (hours * 3600000);
    }

    public static long minutesInMillis(double minutes) {
        return (long) (minutes * 60000);
    }

    public static long secondsInMillis(double seconds) {
        return (long) (seconds * 1000);
    }

    public static String durationInString(long duration) {
        long check = duration - System.currentTimeMillis();
        if (check <= 0) return Language.getAndReplaceNoPrefix("negative");
        else if (duration == -1L) return Language.getAndReplaceNoPrefix("permanent");
        else {
            SimpleDateFormat today = new SimpleDateFormat("dd.MM.yyyy");
            today.format(System.currentTimeMillis());
            SimpleDateFormat future = new SimpleDateFormat("dd.MM.yyyy");
            future.format(duration);
            long time = future.getCalendar().getTimeInMillis() - today.getCalendar().getTimeInMillis();
            int days = (int) (time / 86400000L);
            int hours = (int) (time / 3600000L % 24L);
            int minutes = (int) (time / 60000L % 60L);
            String day = Language.getAndReplaceNoPrefix("days");
            if (days == 1) day = Language.getAndReplaceNoPrefix("day");
            String hour = Language.getAndReplaceNoPrefix("hours");
            if (hours == 1) hour = Language.getAndReplaceNoPrefix("hour");
            String minute = Language.getAndReplaceNoPrefix("minutes");
            if (minutes == 2) minute = Language.getAndReplaceNoPrefix("minute");
            if (minutes < 1 && days == 0 && hours == 0) return Language.getAndReplaceNoPrefix("seconds");
            else if (hours == 0 && days == 0) return minutes + " " + minute;
            else return days == 0 ? hours + " " + hour + " " + minutes + " " + minute : days + " " + day + " " + hours + " " + hour + " " + minutes + " " + minute;
        }
    }
}
