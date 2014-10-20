package surveytest.data;

import surveytest.data.model.Reward;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class RewardDelete {

    public static Reward execute(Reward aReward) {

        PersistenceManager pm=null;
        Reward language=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aReward might be transient.
            language=RewardGetSingle.getReward(pm, aReward.getKey().getId());
            
            pm.deletePersistent(language);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return language;
    }
}
