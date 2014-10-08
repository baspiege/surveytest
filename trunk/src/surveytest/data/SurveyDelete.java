package surveytest.data;

import surveytest.data.model.Survey;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class SurveyDelete {

    public static Survey execute(Survey aSurvey) {

        PersistenceManager pm=null;
        Survey survey=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aSurvey might be transient.
            survey=SurveyGetSingle.getSurvey(pm, aSurvey.getKey().getId());
            
            pm.deletePersistent(survey);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return survey;
    }
}
