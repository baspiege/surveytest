package surveytest.data;

import surveytest.data.model.Question;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Get question.
 *
 * @author Brian Spiegel
 */
public class QuestionGetSingle {

    /**
     * Get question.
     *
     * @param aQuestionId question Id
     * @return a question
     * @since 1.0
     */
    public static Question execute(Long aQuestionId) {
        PersistenceManager pm=null;
        Question question=null;
        try {
            pm=PMF.get().getPersistenceManager();
            question=QuestionGetSingle.getQuestion(pm,aQuestionId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return question;
    }

    /**
     * Get a question.
     *
     * @param aPm PersistenceManager
     * @param aQuestionId question Id
     * @return a question null if not found
     *
     * @since 1.0
     */
    public static Question getQuestion(PersistenceManager aPm, long aQuestionId) {
        return aPm.getObjectById(Question.class, aQuestionId);
    }
}
