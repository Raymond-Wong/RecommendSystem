package Executors;

import Config.Const;
import Utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raymondwong on 16-1-4.
 */
public class Similarity {

    /**
     * 相似性矩阵
     */
    private Map<String, Map<String, Double>> similarity = new HashMap<String, Map<String, Double>>();
    /**
     * 喜欢每个item的用户数量
     */
    private Map<String, Integer> item2UsersAmount;
    /**
     * 共线矩阵
     */
    private Map<String, HashMap<String, Integer>> coOccurence;

    private class Runner extends Thread {

        int start;
        int end;

        Runner(int start, int end) {
            super();
            this.start = start;
            this.end = end;
            start();
        }

        public void run() {
            int looper = -1;
            for (String itemA : coOccurence.keySet()) {
                ++looper;
                if (looper < start || looper >= end) continue;
                for (String itemB : coOccurence.get(itemA).keySet()) {
                    synchronized (similarity) {
                        if (!similarity.containsKey(itemA))
                            similarity.put(itemA, new HashMap<String, Double>());
                        if (!similarity.containsKey(itemB))
                            similarity.put(itemB, new HashMap<String, Double>());
                        double sim = coOccurence.get(itemA).get(itemB) / Math.sqrt(item2UsersAmount.get(itemA) * item2UsersAmount.get(itemB));
                        similarity.get(itemA).put(itemB, sim);
                        similarity.get(itemB).put(itemA, sim);
                    }
                }
            }
        }

    }

    public void exe(Map<String, Integer> item2UsersAmount, Map<String, HashMap<String, Integer>> coOccurence) throws Exception {
        long startTime = System.currentTimeMillis();
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "开始构造相似性矩阵");
        setCoOccurence(coOccurence);
        setItem2UsersAmount(item2UsersAmount);
        Runner[] runners = new Runner[Const.THREADS_AMOUNT];
        int jobsAmount = item2UsersAmount.size();
        int start = 0, end = 0;
        for (int i = 0; i < Const.THREADS_AMOUNT; i++) {
            start = end;
            end = start + jobsAmount / Const.THREADS_AMOUNT;
            end = end > jobsAmount ? jobsAmount : end;
            runners[i] = new Runner(start, end);
        }
        for (Runner runner : runners) {
            runner.join();
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "完成构造相似性矩阵");
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "相似性矩阵阶段共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
    }

    @org.junit.Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();
        PreCondition preCondition = new PreCondition();
        CoOccurence coOccurence = new CoOccurence();
        preCondition.exe("/home/raymondwong/code/recommendersystem/data/rec_log_train_positive.txt");
        coOccurence.exe(preCondition.getUser2ItemsStr(), preCondition.getItem2UserAmount());
        exe(preCondition.getItem2UserAmount(), coOccurence.getCoOccurenceMatirx());
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
        System.out.println(similarity);
    }


    public Map<String, Map<String, Double>> getSimilarity() {
        return similarity;
    }

    public void setItem2UsersAmount(Map<String, Integer> item2UsersAmount) {
        this.item2UsersAmount = item2UsersAmount;
    }

    public void setCoOccurence(Map<String, HashMap<String, Integer>> coOccurence) {
        this.coOccurence = coOccurence;
    }
}
