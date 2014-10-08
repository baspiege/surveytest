package surveytest.data;

import surveytest.data.model.AnswerText;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class AnswerTextGetSingle {

    public static AnswerText execute(Long aAnswerTextId) {
        PersistenceManager pm=null;
        AnswerText answerText=null;
        try {
            pm=PMF.get().getPersistenceManager();
            answerText=AnswerTextGetSingle.getAnswerText(pm,aAnswerTextId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerText;
    }

    /**
     * @return a answerText null if not found
     */
    public static AnswerText getAnswerText(PersistenceManager aPm, long aAnswerTextId) {
        return aPm.getObjectById(AnswerText.class, aAnswerTextId);
    }
}
