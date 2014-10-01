package surveytest.data;

import surveytest.data.model.SurveyResponse;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SurveyResponseGetAll {

    public static List<SurveyResponse> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<SurveyResponse> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(SurveyResponse.class);

                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");
                
                results = (List<SurveyResponse>) query.execute(aSurveyId);

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
