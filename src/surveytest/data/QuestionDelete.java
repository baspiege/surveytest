package surveytest.data;

import surveytest.data.model.Question;
import javax.jdo.PersistenceManager;

/**
 * Delete question
 *
 * @author Brian Spiegel
 */
public class QuestionDelete {

    /**
     * Delete question.
     *
     * @param aQuestion the question to delete
     *
     * @since 1.0
     */
    public static void execute(Question aQuestion) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            if (aQuestion!=null){            
                aQuestion=QuestionGetSingle.getQuestion(pm,aQuestion.getKey().getId());
                pm.deletePersistent(aQuestion);
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
    }
}
