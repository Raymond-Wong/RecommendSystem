package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by raymondwong on 16-1-3.
 */
public class FileUtils {

    /**
     * 获取文件行数
     * @param path 文件路径
     * @return 文件行数
     * @throws IOException
     */
    public static int getFileLinesAmount(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File(path)));
        String line = bf.readLine();
        int counter = 0;
        while (line != null) {
            ++counter;
            line = bf.readLine();
        }
        bf.close();
        return counter;
    }

    /**
     * 读取某个文件的具体某行
     * @param path 文件路径
     * @param lineNum 行数
     * @return
     */
    public static String getFileLine(String path, int lineNum) throws IOException {
        return org.apache.commons.io.FileUtils.readLines(new File(path)).get(lineNum);
    }

    @org.junit.Test
    public void test() throws IOException {
        System.out.println(getFileLine("/home/raymondwong/code/recommendersystem/data/rec_log_train_10.txt", 9));
    }

}
