package surveytest.data;

import surveytest.data.model.QuestionText;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get questionText.
 *
 * @author Brian Spiegel
 */
public class QuestionTextGetSingle {

    /**
     * Get questionText.
     *
     * @param aQuestionTextId questionText Id
     * @return a questionText
     * @since 1.0
     */
    public static QuestionText execute(Long aQuestionTextId) {
        PersistenceManager pm=null;
        QuestionText questionText=null;
        try {
            pm=PMF.get().getPersistenceManager();
            questionText=QuestionTextGetSingle.getQuestionText(pm,aQuestionTextId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return questionText;
    }

    /**
     * Get a questionText.
     *
     * @param aPm PersistenceManager
     * @param aQuestionTextId questionText Id
     * @return a questionText null if not found
     *
     * @since 1.0
     */
    public static QuestionText getQuestionText(PersistenceManager aPm, long aQuestionTextId) {
        return aPm.getObjectById(QuestionText.class, aQuestionTextId);
    }
}
