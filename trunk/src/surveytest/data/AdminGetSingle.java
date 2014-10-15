package surveytest.data;

import surveytest.data.model.Admin;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AdminGetSingle {

    public static Admin execute(Long aAdminId) {
        PersistenceManager pm=null;
        Admin admin=null;
        try {
            pm=PMF.get().getPersistenceManager();
            admin=AdminGetSingle.getAdmin(pm,aAdminId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return admin;
    }

    /**
     * @return a admin null if not found
     */
    public static Admin getAdmin(PersistenceManager aPm, long aAdminId) {
        return aPm.getObjectById(Admin.class, aAdminId);
    }
    
    public static Admin getByUserId(String aUserId, long surveyId) {
        PersistenceManager pm=null;
        Admin result=null;
        List<Admin> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Admin.class);
                query.setFilter("userIdLowerCase==userIdLowerCaseParam && surveyId==surveyIdParam");
                query.declareParameters("String userIdLowerCaseParam, Long surveyIdParam");

                results = (List<Admin>) query.execute(aUserId.toLowerCase(), surveyId);

                // Touch object to get data.  Size method triggers the underlying database call.
                results.size();
            } finally {
                if (query!=null) {
                    query.closeAll();
                }
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        
        if (results!=null && !results.isEmpty()) {
            result=results.get(0);
        }
        
        return result;
    }
}
