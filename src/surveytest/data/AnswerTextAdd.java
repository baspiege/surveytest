package surveytest.data;

import surveytest.data.model.AnswerText;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerTextAdd {

    public static AnswerText execute(AnswerText aAnswerText) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aAnswerText.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aAnswerText);
            
            // History
            //AnswerTextHistory questionTextHistory=new AnswerTextHistory(aAnswerText);
            //pm.makePersistent(questionTextHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAnswerText;
    }
}
