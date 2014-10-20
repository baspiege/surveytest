package surveytest.data;

import surveytest.data.model.Reward;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class RewardUpdate {

    public static Reward execute(Reward aReward) {

        PersistenceManager pm=null;
        Reward reward=null;
        try {
            pm=PMF.get().getPersistenceManager();
            
            // Get managed instance.  aReward might be transient.
            reward=RewardGetSingle.getReward(pm, aReward.getKey().getId());
            
            if (reward!=null) {
                reward.setUrl(aReward.getUrl());
                reward.setLastUpdateUserId(aReward.getLastUpdateUserId());
                reward.setLastUpdateTime(new Date());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return reward;
    }
}
