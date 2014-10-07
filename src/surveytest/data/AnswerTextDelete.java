package surveytest.data;

import surveytest.data.model.AnswerText;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerTextDelete {

    public static AnswerText execute(AnswerText aAnswerText) {

        PersistenceManager pm=null;
        AnswerText answerText=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswerText might be transient.
            answerText=AnswerTextGetSingle.getAnswerText(pm, aAnswerText.getKey().getId());
            
            pm.deletePersistent(answerText);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerText;
    }
}
