package Executors;

import Config.Const;
import Utils.CommonUtils;

import java.util.*;

/**
 * Created by raymondwong on 16-1-7.
 */
public class Recommend {

    private String uid;
    private Set<String> likeSet;
    private Map<String, Integer> item2UsersAmount = null;
    private Map<String, Map<String, Double>> similarity = null;
    private List<String> recommendList = new ArrayList<String>();
    private Map<String, Double> likeDegree = new HashMap<String, Double>();

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
            for (String itemA : item2UsersAmount.keySet()) {
                ++looper;
                if (looper < start || looper >= end) continue;
                Double ld = 0d;
                Map<String, Double> similarSet = sortMap(getSimilarSet(itemA), -1);
                int innerLooper = 0;
                for (String itemB : similarSet.keySet()) {
                    if (innerLooper++ >= Const.TO_RECOMMEND_AMOUNT) break;
                    if (!likeSet.contains(itemB)) continue;
                    ld += similarSet.get(itemB);
                }
                synchronized (likeDegree) {
                    likeDegree.put(itemA, ld);
                }
            }
        }

    }

    public void exe(String uid, String[] likeSet, Map<String, Integer> item2UsersAmount, Map<String, Map<String, Double>> similarity) throws Exception {
        CommonUtils.logger(this.getClass(), CommonUtils.Type.DEBUG, "开始给 " + uid + " 推荐 " + Const.TO_RECOMMEND_AMOUNT + " items");
        long startTime = System.currentTimeMillis();
        setUid(uid);
        setLikeSet(likeSet);
        setItem2UsersAmount(item2UsersAmount);
        setSimilarity(similarity);
        Runner[] runners = new Runner[Const.THREADS_AMOUNT];
        int jobsAmount = item2UsersAmount.size();
        int start = 0, end = 0;
        for (int i = 0; i < Const.THREADS_AMOUNT; i++) {
            start = 0;
            end = start + jobsAmount / Const.THREADS_AMOUNT;
            end = end > jobsAmount ? jobsAmount : end;
            runners[i] = new Runner(start, end);
        }
        for (Runner runner : runners) {
            runner.join();
        }
        likeDegree = sortMap(likeDegree, -1);
        int looper = 0;
        for (String itemId : likeDegree.keySet()) {
            if (looper >= Const.TO_RECOMMEND_AMOUNT) break;
            recommendList.add(itemId);
            ++looper;
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.DEBUG, "结束给 " + uid + " 推荐items");
        CommonUtils.logger(this.getClass(), CommonUtils.Type.DEBUG, "给 " + uid + " 推荐 " + Const.TO_RECOMMEND_AMOUNT + " 个items共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
    }

    private Map<String, Double> getSimilarSet(String item) {
        if (!similarity.containsKey(item))
            return new HashMap<String, Double>();
        Map<String, Double> similarSet = similarity.get(item);
        return similarSet;
    }

    public static Map<String, Double> sortMap(Map<String, Double> oldMap, final int dir) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                Double v1= o1.getValue(), v2 = o2.getValue();
                int ret = dir * (v1 > v2 ? 1 : (v1 == v2 ? 0 : -1));
                return ret;
            }

        });
        Map newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }

    @org.junit.Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();
        PreCondition preCondition = new PreCondition();
        CoOccurence coOccurence = new CoOccurence();
        Similarity similarity = new Similarity();
        preCondition.exe("/home/raymondwong/code/recommendersystem/data/rec_log_train_10000.txt");
        coOccurence.exe(preCondition.getUser2ItemsStr(), preCondition.getItem2UserAmount());
        similarity.exe(preCondition.getItem2UserAmount(), coOccurence.getCoOccurenceMatirx());
        for (String uid : preCondition.getUser2ItemsStr().keySet()) {
            exe(uid, preCondition.getUser2ItemsStr().get(uid).split(","), preCondition.getItem2UserAmount(), similarity.getSimilarity());
            break;
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "共耗时 " + CommonUtils.prettyTimeDiff(startTime, System.currentTimeMillis()));
        System.out.println(likeDegree);
        System.out.println(recommendList);
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLikeSet(String[] mlikeSet) {
        likeSet = new HashSet<String>();
        for (String item : mlikeSet) {
            likeSet.add(item);
        }
    }

    public void setItem2UsersAmount(Map<String, Integer> item2UsersAmount) {
        if (this.item2UsersAmount != null) return;
        this.item2UsersAmount = item2UsersAmount;
    }

    public void setSimilarity(Map<String, Map<String, Double>> similarity) {
        if (this.similarity != null) return;
        this.similarity = similarity;
    }

    public List<String> getRecommendList() {
        return recommendList;
    }
}
