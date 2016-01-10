package Config;

import Jama.Matrix;

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

    /**
     * 是否在调试模式
     */
    public static final boolean IS_DEBUG = false;

    /**
     * 待推荐的人数
     */
    public static final int TO_RECOMMEND_AMOUNT = 10;

    /**
     * resource文件夹的路径
     */
    public static final String RESOURCES_PATH = "/home/raymondwong/code/IdeaProjects/RecommendSystem/src/main/resources/";


    @org.junit.Test
    public void test() {
        double[][] arr = {{1., 2.}, {2., 3.}};
        Matrix A = new Matrix(arr);
        System.out.println(A.getArray());
    }

}
