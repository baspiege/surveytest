package surveytest.data;

import surveytest.data.model.SurveyResponse;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SurveyResponseGetSingle {

    public static SurveyResponse execute(Long aSurveyResponseId) {
        PersistenceManager pm=null;
        SurveyResponse surveyResponse=null;
        try {
            pm=PMF.get().getPersistenceManager();
            surveyResponse=SurveyResponseGetSingle.getSurveyResponse(pm,aSurveyResponseId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return surveyResponse;
    }

    /**
     * @return a surveyResponse or null if not found
     */
    public static SurveyResponse getSurveyResponse(PersistenceManager aPm, long aSurveyResponseId) {
        return aPm.getObjectById(SurveyResponse.class, aSurveyResponseId);
    }
}
