package surveytest.data;

import surveytest.data.model.Reward;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class RewardGetSingle {

    public static Reward execute(Long aRewardId) {
        PersistenceManager pm=null;
        Reward reward=null;
        try {
            pm=PMF.get().getPersistenceManager();
            reward=RewardGetSingle.getReward(pm,aRewardId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return reward;
    }

    /**
     * @return a reward null if not found
     */
    public static Reward getReward(PersistenceManager aPm, long aRewardId) {
        return aPm.getObjectById(Reward.class, aRewardId);
    }
}
