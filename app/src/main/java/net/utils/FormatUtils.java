package net.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

    public static String formatDate(Date date){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }

}
