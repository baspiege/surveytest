package surveytest.data;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuestionTextGetAll {

    public static List<QuestionText> execute(Long aQuestionId) {
        PersistenceManager pm=null;
        List<QuestionText> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(QuestionText.class);
                query.setFilter("questionId==questionIdParam");
                query.declareParameters("long questionIdParam");

                results = (List<QuestionText>) query.execute(aQuestionId);

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
    
    public static List<QuestionText> executeBySurveyId(Long aSurveyId) {
        PersistenceManager pm=null;
        List<QuestionText> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(QuestionText.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                results = (List<QuestionText>) query.execute(aSurveyId);

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
    
    public static List<QuestionText> executeByLanguageId(Long aLanguageId) {
        PersistenceManager pm=null;
        List<QuestionText> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(QuestionText.class);
                query.setFilter("languageId==languageIdParam");
                query.declareParameters("long languageIdParam");

                results = (List<QuestionText>) query.execute(aLanguageId);

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
