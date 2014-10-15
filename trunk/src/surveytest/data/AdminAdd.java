package surveytest.data;

import surveytest.data.model.Admin;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AdminAdd {

    public static Admin execute(Admin aAdmin) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aAdmin.setLastUpdateTime(new Date());
            
            pm.makePersistent(aAdmin);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAdmin;
    }
}
