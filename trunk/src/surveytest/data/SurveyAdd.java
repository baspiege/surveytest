package surveytest.data;

import surveytest.data.model.Survey;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class SurveyAdd {

    public static Survey execute(Survey aSurvey) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aSurvey.setLastUpdateTime(new Date());

            pm.makePersistent(aSurvey);            
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aSurvey;
    }
}
