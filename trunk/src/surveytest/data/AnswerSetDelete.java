package surveytest.data;

import surveytest.data.model.AnswerSet;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerSetDelete {

    public static AnswerSet execute(AnswerSet aAnswerSet) {

        PersistenceManager pm=null;
        AnswerSet answerSet=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswerSet might be transient.
            answerSet=AnswerSetGetSingle.getAnswerSet(pm, aAnswerSet.getKey().getId());
            
            pm.deletePersistent(answerSet);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerSet;
    }
}
