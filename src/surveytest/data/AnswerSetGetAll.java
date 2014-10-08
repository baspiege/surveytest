package surveytest.data;

import surveytest.data.model.AnswerSet;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerSetGetAll {

    public static List<AnswerSet> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<AnswerSet> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(AnswerSet.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                // Sorting
                query.setOrdering("descriptionLowerCase ASC");

                results = (List<AnswerSet>) query.execute(aSurveyId);

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
