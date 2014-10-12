package surveytest.data;

import surveytest.data.model.QuestionText;
import surveytest.data.model.QuestionTextHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionTextDelete {

    public static QuestionText execute(QuestionText aQuestionText) {

        PersistenceManager pm=null;
        QuestionText questionText=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aQuestionText might be transient.
            questionText=QuestionTextGetSingle.getQuestionText(pm, aQuestionText.getKey().getId());
            
            pm.deletePersistent(questionText);
            
            aQuestionText.setLastUpdateTime(new Date());
            aQuestionText.setLastUpdateUserId(aQuestionText.getLastUpdateUserId());
            QuestionTextHistory questionTextHistory=new QuestionTextHistory(aQuestionText, DataConstants.DELETE);
            pm.makePersistent(questionTextHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return questionText;
    }
}
