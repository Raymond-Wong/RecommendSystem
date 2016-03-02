import Config.Const;
import Executor.DataHandler;
import Executor.TrainHandler;
import Executor.ValidateHandler;
import Util.CommonUtils;
import Util.MatrixUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by raymondwong on 16-3-1.
 */
public class Main {

    public static void main(String[] argvs) {
        try {
            // 0. 将原始训练集以2:8的比例分割成测试集和训练集
            DataHandler.splitDataSet(Const.RAW_TRAIN_DATA_PATH);
            // 1. 获取用户集合和物品集合
            List<String> users = DataHandler.getColumn(Const.TMP_TRAIN_DATA_PATH, 0);
            List<String> items = DataHandler.getColumn(Const.TMP_TRAIN_DATA_PATH, 1);
            // 2. 获取目标矩阵
            Float[][] targetMatrix = DataHandler.getTargetMatrix(Const.TMP_TRAIN_DATA_PATH, users, items);
            // 3. 随机生成用户到特征向量的映射矩阵以及特征向量到物品的映射矩阵
            Float[][] userMatrix = MatrixUtils.randomMatrix(users.size(), Const.FEATURES_AMOUNT);
            Float[][] itemMatrix = MatrixUtils.randomMatrix(Const.FEATURES_AMOUNT, items.size());
            // 4. 开始迭代训练矩阵
            int iterateTime = 0;
            while (++iterateTime <= Const.MAX_ITERATE_TIME) {
                // 4.1 调整两个矩阵
                TrainHandler.train(userMatrix, itemMatrix, targetMatrix, users.size(), items.size());
                // 4.2 获取此时的误差
                Float error = TrainHandler.getError(userMatrix, itemMatrix, targetMatrix, users.size(), items.size());
                // 4.3 如果错误小于指定值则跳出
                if (error < Const.MIN_ERROR) break;
                CommonUtils.logger(Main.class, CommonUtils.LogType.DEBUG, "第 " + iterateTime + " 次训练后的误差值为 " + error);
            }
            // 5. 输出结果矩阵以验证
            // 5.1 计算预测结果的精确度
            Float precision = ValidateHandler.getPrecision(targetMatrix, userMatrix, itemMatrix);
            CommonUtils.logger(Main.class, CommonUtils.LogType.INFO, "预测的精确度为 " + (precision * 100) + "%");
            // 5.2 计算预测结果的召回率
            Float recall = ValidateHandler.getRecall(userMatrix, itemMatrix, users, items);
            CommonUtils.logger(Main.class, CommonUtils.LogType.INFO, "预测的精确度为 " + (recall * 100) + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void test() {
        Integer[][] arr = new Integer[1][1];
        arr[0][0] = 1;
        changeArr(arr);
        System.out.println(arr[0][0]);
    }

    private void changeArr(Integer[][] arr) {
        arr[0][0] += 1;
    }

}
