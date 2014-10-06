package surveytest.data;

import surveytest.data.model.AnswerText;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get answerText.
 *
 * @author Brian Spiegel
 */
public class AnswerTextGetSingle {

    /**
     * Get answerText.
     *
     * @param aAnswerTextId answerText Id
     * @return a answerText
     * @since 1.0
     */
    public static AnswerText execute(Long aAnswerTextId) {
        PersistenceManager pm=null;
        AnswerText answerText=null;
        try {
            pm=PMF.get().getPersistenceManager();
            answerText=AnswerTextGetSingle.getAnswerText(pm,aAnswerTextId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerText;
    }

    /**
     * Get a answerText.
     *
     * @param aPm PersistenceManager
     * @param aAnswerTextId answerText Id
     * @return a answerText null if not found
     *
     * @since 1.0
     */
    public static AnswerText getAnswerText(PersistenceManager aPm, long aAnswerTextId) {
        return aPm.getObjectById(AnswerText.class, aAnswerTextId);
    }
}
