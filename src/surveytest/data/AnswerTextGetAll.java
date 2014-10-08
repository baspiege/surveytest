package surveytest.data;

import surveytest.data.model.Answer;
import surveytest.data.model.AnswerText;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerTextGetAll {

    public static List<AnswerText> execute(Long aAnswerId) {
        PersistenceManager pm=null;
        List<AnswerText> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(AnswerText.class);
                query.setFilter("answerId==answerIdParam");
                query.declareParameters("long answerIdParam");

                results = (List<AnswerText>) query.execute(aAnswerId);

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
    
    public static List<AnswerText> executeBySurveyId(Long aSurveyId) {
        PersistenceManager pm=null;
        List<AnswerText> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(AnswerText.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                results = (List<AnswerText>) query.execute(aSurveyId);

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
