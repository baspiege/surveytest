package surveytest.data;

import surveytest.data.model.Survey;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SurveyGetAll {

    public static List<Survey> execute() {
        PersistenceManager pm=null;
        List<Survey> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Survey.class);
                query.setOrdering("nameLowerCase ASC");

                results = (List<Survey>) query.execute();

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
