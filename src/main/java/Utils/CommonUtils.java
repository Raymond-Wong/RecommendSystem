package Utils;

import Config.Const;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

/**
 * Created by raymondwong on 16-1-4.
 */
public class CommonUtils {

    public enum Type {
        DEBUG, INFO, WARN, ERROR;
    }

    /**
     * 用于输出日志文件
     * @param c 输出该信息的类
     * @param type 信息类别
     * @param msg 信息本身
     */
    public static void logger(Class c, Type type, String msg) {
        if (!Const.IS_DEBUG && type.equals(Type.DEBUG)) return;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + c.getName() + "][" + type + "][" + sdf.format(new Date()) + "]\t" + msg);
    }

    @org.junit.Test
    public void test() throws Exception {
        logger(this.getClass(), Type.INFO, "asdf");
    }


    /**
     * 格式化时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public static String prettyTimeDiff(long startTime, long endTime) throws Exception {
        long diff = endTime - startTime;
        if (diff < 0) throw new Exception();
        String[] resultArr = "0/0/0/0/0".split("/");
        String[] unitArr = "天/时/分/秒/毫秒".split("/");
        String[] stepLen = "24/60/60/1000".split("/");
        for (int index = 4; index >= 1; index--) {
            resultArr[index] = String.valueOf(diff % Integer.parseInt(stepLen[index - 1]));
            diff /= Integer.parseInt(stepLen[index - 1]);
        }
        resultArr[0] = String.valueOf(diff);
        String result = "";
        for (int i = 0; i < 5; i++) {
            if (Integer.parseInt(resultArr[i]) == 0) continue;
            result += resultArr[i] + unitArr[i];
        }
        return result;
    }


}
