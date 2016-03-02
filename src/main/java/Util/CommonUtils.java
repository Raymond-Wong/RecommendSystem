package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raymondwong on 16-3-2.
 */
public class CommonUtils {

    public enum LogType {
        DEBUG, WARN, ERROR, INFO
    }

    public static void logger(Class clazz, LogType type, String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + clazz + "][" + type + "][" + sdf.format(new Date()) + "]\t" + msg);
    }

}
