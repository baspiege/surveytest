package surveytest.data;

import surveytest.data.model.Answer;
//import surveytest.data.model.AnswerHistory;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerAdd {

    public static Answer execute(Answer aAnswer) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Save
            pm.makePersistent(aAnswer);
            
            // History
            //AnswerHistory answerHistory=new AnswerHistory(aAnswer);
            //pm.makePersistent(answerHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAnswer;
    }
}
