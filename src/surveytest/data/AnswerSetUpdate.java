package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.AnswerSet;

public class AnswerSetUpdate {

    public static AnswerSet execute(AnswerSet aAnswerSet) {

        AnswerSet answerSet=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswerSet might be transient.
            answerSet=AnswerSetGetSingle.getAnswerSet(pm,aAnswerSet.getKey().getId());

            if (answerSet!=null){

                if (aAnswerSet.getDescription()!=null) {
                    answerSet.setDescription(aAnswerSet.getDescription());
                }
                
                answerSet.setLastUpdateTime(new Date());
                answerSet.setLastUpdateUserId(aAnswerSet.getLastUpdateUserId());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerSet;
    }
}
