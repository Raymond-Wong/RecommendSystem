package Util;

import Config.Const;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

/**
 * Created by raymondwong on 16-3-2.
 */
public class MatrixUtils {

    /**
     * 打印一个Float的二维数组
     * @param matrix
     */
    public static void print(Float[][] matrix) {
        for (Float[] row : matrix) {
            for (Float cell : row) {
                System.out.printf(String.valueOf(cell) + " ");
            }
            System.out.printf("\n");
        }
    }

    /**
     * 获取指定规模的随机矩阵
     * @param rowsAmount
     * @param colsAmount
     * @return
     */
    public static Float[][] randomMatrix(int rowsAmount, int colsAmount) {
        Random random = new Random((new Date()).getTime());
        Float[][] ret = new Float[rowsAmount][colsAmount];
        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < colsAmount; j++) {
                ret[i][j] = Math.abs(random.nextFloat());
            }
        }
        return ret;
    }

    /**
     * 矩阵的乘法
     * @param userMatrix
     * @param itemMatrix
     * @return
     */
    public static Float[][] dot(Float[][] userMatrix, Float[][] itemMatrix) {
        int row = userMatrix.length, col = itemMatrix[0].length;
        Float[][] ret = new Float[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Float cell = 0.0f;
                for (int k = 0; k < userMatrix[0].length; k++) {
                    cell += userMatrix[r][k] * itemMatrix[k][c];
                }
                ret[r][c] = cell;
            }
        }
        return ret;
    }

    @org.junit.Test
    public void test() throws IOException {
        String path = "/home/raymondwong/code/recommendersystem/data/rec_log_train_100.txt";
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        Set<String> users = new HashSet<String>();
        Set<String> items = new HashSet<String>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] arr = line.split("\t");
            users.add(arr[0]);
            items.add(arr[1]);
        }
        System.out.println(users.size());
        System.out.println(items.size());
    }
}
