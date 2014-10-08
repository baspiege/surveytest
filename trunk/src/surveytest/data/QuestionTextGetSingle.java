package surveytest.data;

import surveytest.data.model.QuestionText;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuestionTextGetSingle {

    public static QuestionText execute(Long aQuestionTextId) {
        PersistenceManager pm=null;
        QuestionText questionText=null;
        try {
            pm=PMF.get().getPersistenceManager();
            questionText=QuestionTextGetSingle.getQuestionText(pm,aQuestionTextId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return questionText;
    }

    /**
     * @return a questionText null if not found
     */
    public static QuestionText getQuestionText(PersistenceManager aPm, long aQuestionTextId) {
        return aPm.getObjectById(QuestionText.class, aQuestionTextId);
    }
}
