package Executors;

import Config.Const;
import Utils.CommonUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by raymondwong on 16-1-4.
 */
public class CoOccurence {

    /**
     * 共现矩阵
     */
    private HashMap<String, HashMap<String, Integer>> coOccurenceMatirx = new HashMap<String, HashMap<String, Integer>>();
    /**
     * 用户物品倒排表
     */
    private Map<String, String> user2ItemStr;
    /**

     * 喜欢每个item的用户数量
     */
    private Map<String, Integer> item2UsersAmount;

    private class Runner extends Thread {

        int start = 0;
        int end = 0;

        Runner(int start, int end) {
            super();
            this.start = start;
            this.end = end;
            start();
        }

        public void run() {
            int looper = -1;
            for (String uid : user2ItemStr.keySet()) {
                ++looper;
                if (looper < start || looper >= end) continue;
                String[] items = user2ItemStr.get(uid).split(",");
                for (int i = 0; i < items.length - 1; i++) {
                    for (int j = i + 1; j < items.length; j++) {
                        String itemA = items[i];
                        String itemB = items[j];
                        synchronized (coOccurenceMatirx) {
                            // 对整个矩阵进行预处理
                            if (!coOccurenceMatirx.containsKey(itemA))
                                coOccurenceMatirx.put(itemA, new HashMap<String, Integer>());
                            if (!coOccurenceMatirx.containsKey(itemB))
                                coOccurenceMatirx.put(itemB, new HashMap<String, Integer>());
                            if (!coOccurenceMatirx.get(itemA).containsKey(itemB))
                                coOccurenceMatirx.get(itemA).put(itemB, 0);
                            if (!coOccurenceMatirx.get(itemB).containsKey(itemA))
                                coOccurenceMatirx.get(itemB).put(itemA, 0);
                            // 更新共现矩阵
                            coOccurenceMatirx.get(itemA).put(itemB, coOccurenceMatirx.get(itemA).get(itemB) + 1);
                            coOccurenceMatirx.get(itemB).put(itemA, coOccurenceMatirx.get(itemB).get(itemA) + 1);
                        }
                    }
                }
            }
        }

    }

    public void exe(Map<String, String> user2ItemStr, Map<String, Integer> item2UsersAmount) throws Exception {
        long startTime = System.currentTimeMillis();
        setItem2UsersAmount(item2UsersAmount);
        setUser2ItemStr(user2ItemStr);
        Runner[] runners = new Runner[Const.THREADS_AMOUNT];
        int jobsAmount = user2ItemStr.size();
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "将根据 " + jobsAmount + " 个用户生成共现矩阵");
        int start = 0, end = 0;
        for (int i = 0; i < Const.THREADS_AMOUNT; i++) {
            start = end;
            end = start + (jobsAmount / Const.THREADS_AMOUNT);
            end = (end > jobsAmount ? jobsAmount : end);
            runners[i] = new Runner(start, end);
        }
        for (int i = 0; i < Const.THREADS_AMOUNT; i++) {
            runners[i].join();
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "完成共现矩阵的构造");
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "共线矩阵阶段共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
    }

    @org.junit.Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();
        PreCondition preCondition = new PreCondition();
        preCondition.exe("/home/raymondwong/code/recommendersystem/data/rec_log_train_100.txt");
        setUser2ItemStr(preCondition.getUser2ItemsStr());
//        exe(preCondition.getUser2ItemsStr(), preCondition.getItem2UserAmount());
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
        System.out.println(coOccurenceMatirx);
    }

    public HashMap<String, HashMap<String, Integer>> getCoOccurenceMatirx() {
        return coOccurenceMatirx;
    }

    private void setItem2UsersAmount(Map<String, Integer> item2UsersAmount) {
        this.item2UsersAmount = item2UsersAmount;
    }

    private void setUser2ItemStr(Map<String, String> user2ItemStr) {
        this.user2ItemStr = user2ItemStr;
    }

}
