package surveytest.data;

import surveytest.data.model.Question;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get questions.
 *
 * @author Brian Spiegel
 */
public class QuestionGetAll {

    /**
     * Get question.
     *
     * @param aSurveyId
     * @param aSortBy sort by
     * @since 1.0
     */
    public static List<Question> execute(Long aSurveyId, String aSortBy) {
        PersistenceManager pm=null;
        List<Question> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Question.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                // Sorting
                // if (aSortBy==null || aSortBy.equalsIgnoreCase("name")){
                //    query.setOrdering("noteLowerCase ASC");
                //}

                results = (List<Question>) query.execute(aSurveyId);

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
