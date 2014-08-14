package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Dish;
import surveytest.data.model.DishHistory;

/**
 * Update a dish.
 *
 * @author Brian Spiegel
 */
public class DishUpdate {

    /**
     * Update a dish.
     *
     * @param aDish dish
     * @return an updated dish
     *
     * @since 1.0
     */
    public static Dish execute(Dish aDish) {

        Dish dish=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            dish=DishGetSingle.getDish(pm,aDish.getKey().getId());

            if (dish!=null){

                if (aDish.getNote()!=null) {
                    dish.setNote(aDish.getNote());
                }

                dish.setLastUpdateTime(new Date());
                dish.setUser(aDish.getUser());

                // History
                DishHistory dishHistory=new DishHistory(dish);
                pm.makePersistent(dishHistory);
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return dish;
    }
}
