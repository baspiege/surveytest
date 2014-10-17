package surveytest.data;

import surveytest.data.model.Admin;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AdminUpdate {

    public static Admin execute(Admin aAdmin) {

        PersistenceManager pm=null;
        Admin admin=null;
        try {
            pm=PMF.get().getPersistenceManager();
            
            // Get managed instance.  aAdmin might be transient.
            admin=AdminGetSingle.getAdmin(pm, aAdmin.getKey().getId());
            
            if (admin!=null) {
                admin.setUserId(aAdmin.getUserId());
                admin.setLastUpdateUserId(aAdmin.getLastUpdateUserId());
                admin.setLastUpdateTime(new Date());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return admin;
    }
}
