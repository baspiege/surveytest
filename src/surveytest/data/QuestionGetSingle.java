package surveytest.data;

import surveytest.data.model.Question;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class QuestionGetSingle {

    public static Question execute(Long aQuestionId) {
        PersistenceManager pm=null;
        Question question=null;
        try {
            pm=PMF.get().getPersistenceManager();
            question=QuestionGetSingle.getQuestion(pm,aQuestionId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return question;
    }

    /**
     * @return a question null if not found
     */
    public static Question getQuestion(PersistenceManager aPm, long aQuestionId) {
        return aPm.getObjectById(Question.class, aQuestionId);
    }
}
