package surveytest.data;

import surveytest.data.model.AnswerSet;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerSetGetSingle {

    public static AnswerSet execute(Long aAnswerSet) {
        PersistenceManager pm=null;
        AnswerSet answerSet=null;
        try {
            pm=PMF.get().getPersistenceManager();
            answerSet=AnswerSetGetSingle.getAnswerSet(pm,aAnswerSet);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerSet;
    }

    public static AnswerSet getAnswerSet(PersistenceManager aPm, long aAnswerSet) {
        return aPm.getObjectById(AnswerSet.class, aAnswerSet);
    }
}
