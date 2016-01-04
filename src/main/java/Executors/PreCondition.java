package Executors;

import Config.Const;
import Utils.CommonUtils;
import Utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by raymondwong on 16-1-3.
 */
public class PreCondition {

    /**
     * 保存喜欢每一个物品的用户数量
     */
    private Map<String, Integer> item2UserAmount = new HashMap<String, Integer>();
    /**
     * 保存用户物品倒排表
     */
    private Map<String, String> user2ItemsStr = new HashMap<String, String>();

    public void exe(String path) throws Exception {
        long startTime = System.currentTimeMillis();
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "开始预处理");
        int looper = 0;
        BufferedReader bfReader = new BufferedReader(new FileReader(new File(path)));
        String line = null;
        while ((line = bfReader.readLine()) != null) {
            if ((++looper % 1000) == 0)
                CommonUtils.logger(this.getClass(), CommonUtils.Type.DEBUG, "正在处理第 " + looper + " 行");
            String[] arr = line.split("\t");
            String uid = arr[0], itemId = arr[1], attitude = arr[2];
            // 向user列表中压入当前user
            if (!user2ItemsStr.containsKey(uid))
                user2ItemsStr.put(uid, "");
            // 向item列表中压入当前item
            if (!item2UserAmount.containsKey(itemId))
                item2UserAmount.put(itemId, 0);
            if (attitude.equals("1")) {
                // 更新倒排表
                user2ItemsStr.put(uid, user2ItemsStr.get(uid).equals("") ? itemId : (user2ItemsStr.get(uid).contains(itemId) ? user2ItemsStr.get(uid) : user2ItemsStr.get(uid) + "," + itemId));
                // 更新喜欢当前item的用户数量
                item2UserAmount.put(itemId, item2UserAmount.get(itemId) + 1);
            }
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "获取到 " + user2ItemsStr.size() + " 个用户");
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "获取到 " + item2UserAmount.size() + " 个item");
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "预处理阶段共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
    }

    @org.junit.Test
    public void test() throws Exception {
        exe("/home/raymondwong/code/recommendersystem/data/rec_log_train_1000.txt");
    }

    public Map<String, Integer> getItem2UserAmount() {
        return item2UserAmount;
    }

    public Map<String, String> getUser2ItemsStr() {
        return user2ItemsStr;
    }


}
