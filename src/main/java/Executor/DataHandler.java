package Executor;

import Util.CommonUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created by raymondwong on 16-3-2.
 */
public class DataHandler {

    /**
     * 获取制定路径下第index列的数据
     * @param path
     * @param index
     * @return
     * @throws IOException
     */
    public static List<String> getColumn(String path, Integer index) throws IOException {
        CommonUtils.logger(DataHandler.class, CommonUtils.LogType.INFO, "开始获取 " + path + " 的第 " + index + " 列");
        Set<String> set = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.split("\t");
            set.add(arr[index]);
        }
        List<String> ret = new ArrayList<String>();
        ret.addAll(set);
        CommonUtils.logger(DataHandler.class, CommonUtils.LogType.INFO, "成功获取 " + path + " 的第 " + index + " 列, 共获取到 " + ret.size() + " 个唯一值");
        return ret;
    }

    /**
     * 获取目标评分矩阵
     * @param path
     * @param users
     * @param items
     * @return
     * @throws IOException
     */
    public static Float[][] getTargetMatrix(String path, List<String> users, List<String> items) throws IOException {
        CommonUtils.logger(DataHandler.class, CommonUtils.LogType.INFO, "开始获取目标评分矩阵");
        Float[][] targetMatrix = new Float[users.size()][items.size()];
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.split("\t");
            int userIndex = users.indexOf(arr[0]);
            int itemIndex = items.indexOf(arr[1]);
            targetMatrix[userIndex][itemIndex] = Float.parseFloat(arr[2]);
        }
        CommonUtils.logger(DataHandler.class, CommonUtils.LogType.INFO, "成功获取目标评分矩阵");
        return targetMatrix;
    }
}
