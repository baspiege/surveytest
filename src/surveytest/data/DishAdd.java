package surveytest.data;

import surveytest.data.model.Dish;
import surveytest.data.model.DishHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

/**
 * Add a dish.
 *
 * @author Brian Spiegel
 */
public class DishAdd {

    /**
     * Add a dish.
     *
     * @param aDish a dish to add
     * @return the added dish
     *
     * @since 1.0
     */
    public static Dish execute(Dish aDish) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aDish.setLastUpdateTime(new Date());
            aDish.setYesVote(0l);
            aDish.setReviewCount(0l);

            // Save
            pm.makePersistent(aDish);
            
            // History
            DishHistory dishHistory=new DishHistory(aDish);
            pm.makePersistent(dishHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aDish;
    }
}
