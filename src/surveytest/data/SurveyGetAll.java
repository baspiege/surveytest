package surveytest.data;

import surveytest.data.model.Survey;
import surveytest.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get surveys.
 *
 * @author Brian Spiegel
 */
public class SurveyGetAll {

    /**
     * Get surveys.
     *
     * @param aStart starting position
     * @param aSortBy sort by
     * @since 1.0
     */
    public static List<Survey> execute(Long aStart, String aSortBy) {
        PersistenceManager pm=null;
        List<Survey> results=null;
        try {
            pm=PMF.get().getPersistenceManager();
            Query query=null;
            try {
                query = pm.newQuery(Survey.class);

                // Sorting
                if (aSortBy==null || aSortBy.equalsIgnoreCase("name")){
                    query.setOrdering("name ASC");
                }

                // TODO - Update if needed
                //query.setRange(aStart, aStart+10);

                results = (List<Survey>) query.execute();

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
