package surveytest.data;

import surveytest.data.model.QuestionResponse;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class QuestionResponseAdd {

    public static QuestionResponse execute(QuestionResponse aQuestionResponse) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aQuestionResponse.setLastUpdateTime(new Date());

            pm.makePersistent(aQuestionResponse);
            
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aQuestionResponse;
    }
}
