package surveytest.data;

import surveytest.data.model.AnswerSet;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class AnswerSetAdd {

    public static AnswerSet execute(AnswerSet aAnswerSet) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aAnswerSet.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aAnswerSet);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aAnswerSet;
    }
}
