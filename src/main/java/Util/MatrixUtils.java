package Util;

import Config.Const;

import java.util.Date;
import java.util.Random;

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
                for (int k = 0; k < Const.FEATURES_AMOUNT; k++) {
                    cell += userMatrix[r][k] * itemMatrix[k][c];
                }
                ret[r][c] = cell;
            }
        }
        return ret;
    }

    @org.junit.Test
    public void test() {
        Float[][] a = new Float[1][2];
        Float[][] b = new Float[2][1];
        a[0][0] = 1.0f;
        a[0][1] = 2.0f;
        b[0][0] = 3.0f;
        b[1][0] = 4.0f;
        print(dot(a, b));
    }
}
