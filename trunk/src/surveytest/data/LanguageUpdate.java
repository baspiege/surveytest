package surveytest.data;

import surveytest.data.model.Language;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class LanguageUpdate {

    public static Language execute(Language aLanguage) {

        PersistenceManager pm=null;
        Language language=null;
        try {
            pm=PMF.get().getPersistenceManager();
            
            // Get managed instance.  aLanguage might be transient.
            language=LanguageGetSingle.getLanguage(pm, aLanguage.getKey().getId());
            
            if (language!=null) {
                language.setName(aLanguage.getName());
                language.setIntroText(aLanguage.getIntroText());
                language.setConfirmationText(aLanguage.getConfirmationText());
                language.setSubmitButtonText(aLanguage.getSubmitButtonText());
                language.setSurveyName(aLanguage.getSurveyName());
                language.setIdentifierText(aLanguage.getIdentifierText());
                language.setLastUpdateUserId(aLanguage.getLastUpdateUserId());
                language.setLastUpdateTime(new Date());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return language;
    }
}
