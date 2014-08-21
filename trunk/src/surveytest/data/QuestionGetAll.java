package surveytest.data;

import surveytest.data.model.Question;
import surveytest.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get questiones.
 *
 * @author Brian Spiegel
 */
public class QuestionesGetAll {

    /**
     * Get questiones.
     *
     * @param aStoreId
     * @param aStart starting position
     * @param aSortBy sort by
     * @since 1.0
     */
    public static List<Question> execute(Long aStoreId, Long aStart, String aSortBy) {
        PersistenceManager pm=null;
        List<Question> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Question.class);
                //query.setFilter("surveyId==surveyIdParam");
                //query.declareParameters("long surveyIdParam");

                // Sorting
                // if (aSortBy==null || aSortBy.equalsIgnoreCase("name")){
                //    query.setOrdering("noteLowerCase ASC");
                //}

                //query.setRange(aStart, aStart+10);

                results = (List<Question>) query.execute(aStoreId);

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
