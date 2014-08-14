package surveytest.data;

import surveytest.data.model.Dish;
import javax.jdo.PersistenceManager;

/**
 * Delete dish
 *
 * @author Brian Spiegel
 */
public class DishDelete {

    /**
     * Delete dish.
     *
     * @param aDish the dish to delete
     *
     * @since 1.0
     */
    public static void execute(Dish aDish) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            if (aDish!=null){            
                aDish=DishGetSingle.getDish(pm,aDish.getKey().getId());
                pm.deletePersistent(aDish);
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
    }
}
