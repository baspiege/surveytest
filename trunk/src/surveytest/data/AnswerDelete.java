package surveytest.data;

import surveytest.data.model.Answer;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerDelete {

    public static Answer execute(Answer aAnswer) {

        PersistenceManager pm=null;
        Answer answer=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswer might be transient.
            answer=AnswerGetSingle.getAnswer(pm, aAnswer.getKey().getId());
            
            pm.deletePersistent(answer);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answer;
    }
}
