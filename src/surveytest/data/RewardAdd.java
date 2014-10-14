package surveytest.data;

import surveytest.data.model.Reward;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class RewardAdd {

    public static Reward execute(Reward aReward) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aReward.setLastUpdateTime(new Date());
            
            pm.makePersistent(aReward);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aReward;
    }
}
