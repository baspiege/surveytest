package surveytest.data;

import surveytest.data.model.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AdminGetAll {

    public static List<Admin> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<Admin> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Admin.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");
                query.setOrdering("userIdLowerCase ASC");
                
                results = (List<Admin>) query.execute(aSurveyId);

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
        return results;
    }
}
