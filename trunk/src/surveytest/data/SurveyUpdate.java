package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Survey;

public class SurveyUpdate {

    public static Survey execute(Survey aSurvey) {

        Survey survey=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            survey=SurveyGetSingle.getSurvey(pm,aSurvey.getKey().getId());

            if (survey!=null){

                if (aSurvey.getName()!=null) {
                    survey.setName(aSurvey.getName());
                }

                // survey.setLastUpdateTime(new Date());
                // survey.setUser(aSurvey.getUser());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return survey;
    }
}
