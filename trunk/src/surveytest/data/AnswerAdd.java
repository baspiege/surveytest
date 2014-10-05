package surveytest.data;

import surveytest.data.model.Answer;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerAdd {

    public static Answer execute(Answer aAnswer) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            pm.makePersistent(aAnswer);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAnswer;
    }
}
