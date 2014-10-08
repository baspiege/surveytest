package surveytest.data;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionAdd {

    public static Question execute(Question aQuestion) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aQuestion.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aQuestion);
            
            // History
            QuestionHistory questionHistory=new QuestionHistory(aQuestion);
            pm.makePersistent(questionHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aQuestion;
    }
}
