package surveytest.data;

import surveytest.data.model.QuestionText;
import surveytest.data.model.QuestionTextHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionTextAdd {

    public static QuestionText execute(QuestionText aQuestionText) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aQuestionText.setLastUpdateTime(new Date());
            pm.makePersistent(aQuestionText);
            
            QuestionTextHistory questionTextHistory=new QuestionTextHistory(aQuestionText, DataConstants.ADD);
            pm.makePersistent(questionTextHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aQuestionText;
    }
}
