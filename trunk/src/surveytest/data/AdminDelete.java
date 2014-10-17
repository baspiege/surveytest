package surveytest.data;

import surveytest.data.model.Admin;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AdminDelete {

    public static Admin execute(Admin aAdmin) {

        PersistenceManager pm=null;
        Admin language=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAdmin might be transient.
            language=AdminGetSingle.getAdmin(pm, aAdmin.getKey().getId());
            
            pm.deletePersistent(language);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return language;
    }
}
