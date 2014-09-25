package surveytest.data;

import surveytest.data.model.Survey;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SurveyGetSingle {

    public static Survey execute(Long aSurveyId) {
        PersistenceManager pm=null;
        Survey survey=null;
        try {
            pm=PMF.get().getPersistenceManager();
            survey=SurveyGetSingle.getSurvey(pm,aSurveyId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return survey;
    }

    /**
     * @return a survey or null if not found
     */
    public static Survey getSurvey(PersistenceManager aPm, long aSurveyId) {
        return aPm.getObjectById(Survey.class, aSurveyId);
    }
}
