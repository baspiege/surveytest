package surveytest.data;

import surveytest.data.model.Answer;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerGetSingle {

    public static Answer execute(Long aAnswerId) {
        PersistenceManager pm=null;
        Answer answer=null;
        try {
            pm=PMF.get().getPersistenceManager();
            answer=AnswerGetSingle.getAnswer(pm,aAnswerId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answer;
    }

    /**
     * @return a answer null if not found
     */
    public static Answer getAnswer(PersistenceManager aPm, long aAnswerId) {
        return aPm.getObjectById(Answer.class, aAnswerId);
    }
}
