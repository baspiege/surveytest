package surveytest.data;

import surveytest.data.model.SurveyResponse;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class SurveyResponseAdd {

    public static SurveyResponse execute(SurveyResponse aSurveyResponse) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            aSurveyResponse.setLastUpdateTime(new Date());

            pm.makePersistent(aSurveyResponse);
            
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aSurveyResponse;
    }
}
