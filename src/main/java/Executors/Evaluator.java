package Executors;

import Config.Const;
import Utils.CommonUtils;

import java.util.Map;

/**
 * Created by raymondwong on 16-1-10.
 */
public class Evaluator {


    public void exe() throws Exception {
        PreCondition preCondition = new PreCondition();
        CoOccurence coOccurence = new CoOccurence();
        Similarity similarity = new Similarity();
        Recommend recommend = new Recommend();
        preCondition.exe(Const.RESOURCES_PATH + "data/trainSet.txt");
        coOccurence.exe(preCondition.getUser2ItemsStr(), preCondition.getItem2UserAmount());
        similarity.exe(preCondition.getItem2UserAmount(), coOccurence.getCoOccurenceMatirx());
        PreCondition testCondition = new PreCondition();
        testCondition.exe(Const.RESOURCES_PATH + "data/testSet.txt");
        double n_precision = 0d, n_recall = 0d, hit = 0d;
        int looper = 0;
        Map<String, String> user2ItemsStr = testCondition.getUser2ItemsStr();
        for (String uid : user2ItemsStr.keySet()) {
            if (looper >= 1000) break;
            CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "正在给 " + user2ItemsStr.size() + " 个用户中的第 " + (++looper) + " 推荐 " + Const.TO_RECOMMEND_AMOUNT + " 个items");
            String tu = user2ItemsStr.get(uid);
            recommend.exe(uid, tu.split(","), preCondition.getItem2UserAmount(), similarity.getSimilarity());
            n_recall += tu.split(",").length;
            n_precision += Const.TO_RECOMMEND_AMOUNT;
            for (String item : recommend.getRecommendList()) {
                hit += (tu.indexOf(item) >= 0 ? 1 : 0);
            }
        }
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "准确率 : " + (hit / n_precision));
        CommonUtils.logger(this.getClass(), CommonUtils.Type.INFO, "召回率 : " + (hit / n_recall));
    }

    @org.junit.Test
    public void test() throws Exception {
        exe();
    }

}
