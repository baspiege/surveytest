package surveytest.data;

import surveytest.data.model.AnswerText;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerTextAdd {

    public static AnswerText execute(AnswerText aAnswerText) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aAnswerText.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aAnswerText);
            
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAnswerText;
    }
}
