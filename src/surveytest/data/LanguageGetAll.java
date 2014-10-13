package surveytest.data;

import surveytest.data.model.Language;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get languages.
 */
public class LanguageGetAll {

    public static List<Language> execute(Long aSurveyId) {
        PersistenceManager pm=null;
        List<Language> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Language.class);
                query.setFilter("surveyId==surveyIdParam");
                query.declareParameters("long surveyIdParam");

                query.setOrdering("nameLowerCase ASC");

                results = (List<Language>) query.execute(aSurveyId);

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
