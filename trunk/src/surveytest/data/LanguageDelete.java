package surveytest.data;

import surveytest.data.model.Language;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class LanguageDelete {

    public static Language execute(Language aLanguage) {

        PersistenceManager pm=null;
        Language language=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aLanguage might be transient.
            Language language=LanguageGetSingle.execute(aLanguage.getKey().getId());
            
            pm.deletePersistent(language);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return language;
    }
}
