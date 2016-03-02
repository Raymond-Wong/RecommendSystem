package Config;

/**
 * Created by raymondwong on 16-3-2.
 */
public class Const {

    public static final String BASE_DATA_PATH = "/home/raymondwong/code/IdeaProjects/RecommendSystem/src/main/resources";

    public static final String RAW_TRAIN_DATA_PATH = BASE_DATA_PATH + "/rec_log_train.txt";

    public static final int FEATURES_AMOUNT = 10;

    public static final int MAX_ITERATE_TIME = 5000;

    public static final Float LEARNING_RATE = 0.01f;

    public static final Float OVERFITTING = 0.02f;

    public static final Float MIN_ERROR = 0.001f;

}
