package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Answer;

/**
 * Update an answer.
 *
 * @author Brian Spiegel
 */
public class AnswerUpdate {

    /**
     * Update an answer.
     *
     * @param aAnswer answer
     * @return an updated answer
     *
     * @since 1.0
     */
    public static Answer execute(Answer aAnswer) {

        Answer answer=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswer might be transient.
            answer=AnswerGetSingle.getAnswer(pm,aAnswer.getKey().getId());

            if (answer!=null){

                if (aAnswer.getText()!=null) {
                    answer.setText(aAnswer.getText());
                }

                // answer.setLastUpdateTime(new Date());
                // answer.setUser(aAnswer.getUser());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answer;
    }
}
