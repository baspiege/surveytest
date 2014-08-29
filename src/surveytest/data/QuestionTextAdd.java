package surveytest.data;

import surveytest.data.model.QuestionText;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionTextAdd {

    public static QuestionText execute(QuestionText aQuestionText) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aQuestionText.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aQuestionText);
            
            // History
            //QuestionTextHistory questionTextHistory=new QuestionTextHistory(aQuestionText);
            //pm.makePersistent(questionTextHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aQuestionText;
    }
}
