package surveytest.data;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

/**
 * Add a question.
 *
 * @author Brian Spiegel
 */
public class QuestionAdd {

    /**
     * Add a question.
     *
     * @param aQuestion a question to add
     * @return the added question
     *
     * @since 1.0
     */
    public static Question execute(Question aQuestion) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aQuestion.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aQuestion);
            
            // History
            QuestionHistory questionHistory=new QuestionHistory(aQuestion);
            pm.makePersistent(questionHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aQuestion;
    }
}
