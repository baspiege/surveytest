package surveytest.data;

import surveytest.data.model.QuestionResponse;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuestionResponseGetAll {

    public static List<QuestionResponse> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<QuestionResponse> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(QuestionResponse.class);

                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");
                
                results = (List<QuestionResponse>) query.execute(aSurveyId);

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
