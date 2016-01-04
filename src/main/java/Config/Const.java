package Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raymondwong on 16-1-3.
 */
public class Const {

    /**
     * 多线程的线程数量
     */
    public static final int THREADS_AMOUNT = 1;

    public static final boolean IS_DEBUG = false;

    @org.junit.Test
    public void test() {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        Map<String, String> tmpMap = new HashMap<String, String>();
        tmpMap.put("middle", "right");
        map.put("left", tmpMap);
        System.out.println(map);
        map.get("left").put("middle", "left");
        System.out.println(map);
    }

}
