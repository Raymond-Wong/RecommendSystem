package Executor;

import Config.Const;
import Util.CommonUtils;
import Util.MatrixUtils;

import java.io.*;
import java.util.List;

/**
 * Created by raymondwong on 16-3-2.
 */
public class ValidateHandler {

    /**
     * 获取预测结果的准确度
     * @param targetMatrix
     * @param userMatrix
     * @param itemMatrix
     * @return
     */
    public static Float getPrecision(Float[][] targetMatrix, Float[][] userMatrix, Float[][] itemMatrix) {
        int hit = 0;
        int all = 0;

        Float[][] predictMatrix = MatrixUtils.dot(userMatrix, itemMatrix);
        for (int user = 0; user < userMatrix.length; user++) {
            for (int item = 0; item < itemMatrix[0].length; item++) {
                if (targetMatrix[user][item] == null) continue;
                int predictVal = (predictMatrix[user][item] > 0 ? 1 : -1);
                all++;
                if (targetMatrix[user][item] == predictVal)
                    hit++;
            }
        }
        CommonUtils.logger(ValidateHandler.class, CommonUtils.LogType.INFO, "训练数据中共有 " + all + " 个user-item对");
        CommonUtils.logger(ValidateHandler.class, CommonUtils.LogType.INFO, "预测结果中共有 " + hit + " 个user-item对与训练数据集相符");
        return (float)hit / (float)all;
    }

    public static Float getRecall(Float[][] userMatrix, Float[][] itemMatrix, List<String> users, List<String> items) throws IOException {
        int all = 0;
        int hit = 0;
        Float[][] predictMatrix = MatrixUtils.dot(userMatrix, itemMatrix);
        BufferedReader reader = new BufferedReader(new FileReader(new File(Const.TMP_TEST_DATA_PATH)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.split("\t");
            String user = arr[0], item = arr[1], attitude = arr[2];
            int userIndex = users.indexOf(user), itemIndex = item.indexOf(item);
            if (userIndex < 0 || itemIndex < 0) continue;
            Float predictVal = predictMatrix[userIndex][itemIndex];
            String predictStr = predictVal > 0 ? "1" : "-1";
            all++;
            if (attitude.equals(predictStr))
                hit++;
        }
        CommonUtils.logger(ValidateHandler.class, CommonUtils.LogType.INFO, "测试数据中共有 " + all + " 对user-item对");
        CommonUtils.logger(ValidateHandler.class, CommonUtils.LogType.INFO, "预测结果中共有 " + hit + " 对user-item对与测试数据集相符");
        return (float)hit / (float)all;
    }
}
