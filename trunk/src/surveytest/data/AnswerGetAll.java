package surveytest.data;

import surveytest.data.model.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerGetAll {

    public static List<Answer> execute(Long aAnswerSetId, String aSortBy) {
        PersistenceManager pm=null;
        List<Answer> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Answer.class);
                query.setFilter("answerSetId==answerSetIdParam");
                query.declareParameters("long answerSetIdParam");

                // Sorting
                //if (aSortBy==null || aSortBy.equalsIgnoreCase("name")){
                //    query.setOrdering("descriptionLowerCase ASC");
                //}

                results = (List<Answer>) query.execute(aAnswerSetId);

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
    
    public static List<Answer>  executeBySurveyId(Long aSurveyId, String aSortBy) {
        PersistenceManager pm=null;
        List<Answer> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Answer.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                // Sorting
                //if (aSortBy==null || aSortBy.equalsIgnoreCase("name")){
                //    query.setOrdering("descriptionLowerCase ASC");
                //}

                results = (List<Answer>) query.execute(aSurveyId);

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
