package surveytest.data;

import surveytest.data.model.Survey;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class SurveyAdd {

    public static Survey execute(Survey aSurvey) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            //aSurvey.setLastUpdateTime(new Date());

            // Save
            pm.makePersistent(aSurvey);
            
            // History
            // SurveyHistory surveyHistory=new SurveyHistory(aSurvey);
            //pm.makePersistent(surveyHistory);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aSurvey;
    }
}
