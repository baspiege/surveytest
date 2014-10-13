package surveytest.data;

import surveytest.data.model.Question;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuestionGetAll {

    public static List<Question> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<Question> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Question.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");
                query.setOrdering("textLowerCase ASC");

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
    
    public static List<Question> executeByAnswerSetId(Long aAnswerSetId) {
        PersistenceManager pm=null;
        List<Question> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Question.class);
                query.setFilter("answerSetId==answerSetIdParam");
                query.declareParameters("long answerSetIdParam");
                query.setOrdering("textLowerCase ASC");

                results = (List<Question>) query.execute(aAnswerSetId);

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
