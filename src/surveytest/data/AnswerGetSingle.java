package surveytest.data;

import surveytest.data.model.Answer;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get answer.
 *
 * @author Brian Spiegel
 */
public class AnswerGetSingle {

    /**
     * Get answer.
     *
     * @param aAnswerId answer Id
     * @return a answer
     * @since 1.0
     */
    public static Answer execute(Long aAnswerId) {
        PersistenceManager pm=null;
        Answer answer=null;
        try {
            pm=PMF.get().getPersistenceManager();
            answer=AnswerGetSingle.getAnswer(pm,aAnswerId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answer;
    }

    /**
     * Get a answer.
     *
     * @param aPm PersistenceManager
     * @param aAnswerId answer Id
     * @return a answer null if not found
     *
     * @since 1.0
     */
    public static Answer getAnswer(PersistenceManager aPm, long aAnswerId) {
        return aPm.getObjectById(Answer.class, aAnswerId);
    }
}
