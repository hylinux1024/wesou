package net.angrycode.wesou.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lancelot on 16/8/3.
 */
public class DateUtils {
    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }

    public static long formatGMTText(String dateText) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.US);
        try {
            Date date = format.parse(dateText);
            return date.getTime();
        } catch (ParseException e) {
            LogUtils.e(e);
        }

        return 0;
    }

    public static String formatGMTText2Readable(String dateText) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.US);
        try {
            Date date = format.parse(dateText);
            return format(date);
        } catch (ParseException e) {
            LogUtils.e(e);
        }
        return "";
    }
}
