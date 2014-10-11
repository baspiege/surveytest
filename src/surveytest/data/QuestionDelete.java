package surveytest.data;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionDelete {

    public static Question execute(Question aQuestion) {

        PersistenceManager pm=null;
        Question question=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aQuestion might be transient.
            question=QuestionGetSingle.getQuestion(pm, aQuestion.getKey().getId());
            pm.deletePersistent(question);
            
            // History
            aQuestion.setLastUpdateTime(new Date());
            aQuestion.setLastUpdateUserId(aQuestion.getLastUpdateUserId());
            QuestionHistory questionHistory=new QuestionHistory(aQuestion);
            pm.makePersistent(questionHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return question;
    }
}
