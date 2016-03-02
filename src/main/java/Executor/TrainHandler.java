package Executor;

import Config.Const;

/**
 * Created by raymondwong on 16-3-2.
 */
public class TrainHandler {

    /**
     * 根据误差调整对userMatrix和itemMatrix做一次调整
     * @param userMatrix
     * @param itemMatrix
     * @param targetMatrix
     * @param userAmount
     * @param itemAmount
     */
    public static void train(Float[][] userMatrix, Float[][] itemMatrix, Float[][] targetMatrix, int userAmount, int itemAmount) {
        for (int user = 0; user < userAmount; user++) {
            for (int item = 0; item < itemAmount; item++) {
                if (targetMatrix[user][item] == null) continue;
                Float error = targetMatrix[user][item];
                for (int k = 0; k < Const.FEATURES_AMOUNT; k++) {
                    error -= userMatrix[user][k] * itemMatrix[k][item];
                }
                for (int k = 0; k < Const.FEATURES_AMOUNT; k++) {
                    userMatrix[user][k] += Const.LEARNING_RATE * (error * itemMatrix[k][item] - Const.OVERFITTING * userMatrix[user][k]);
                    itemMatrix[k][item] += Const.LEARNING_RATE * (error * userMatrix[user][k] - Const.OVERFITTING * itemMatrix[k][item]);
                }
            }
        }
    }

    /**
     * 获取当前的错误
     * @param userMatrix
     * @param itemMatrix
     * @param targetMatrix
     * @param userSize
     * @param itemSize
     * @return
     */
    public static Float getError(Float[][] userMatrix, Float[][] itemMatrix, Float[][] targetMatrix, int userSize, int itemSize) {
        Float error = 0.0f;
        for (int user = 0; user < userSize; user++) {
            for (int item = 0; item < itemSize; item++) {
                if (targetMatrix[user][item] == null) continue;
                Float err = 0.0f;
                for (int k = 0; k < Const.FEATURES_AMOUNT; k++) {
                    err += userMatrix[user][k] * itemMatrix[k][item];
                    error += Const.OVERFITTING * (float)(Math.pow(userMatrix[user][k], 2) + Math.pow(itemMatrix[k][item], 2));
                }
                error += (float)Math.pow(targetMatrix[user][item] - err, 2);
            }
        }
        return error;
    }
}
