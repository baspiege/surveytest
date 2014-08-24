package surveytest.data;

import surveytest.data.model.Language;
import java.util.Date;
import javax.jdo.PersistenceManager;

public class LanguageAdd {

    public static Survey execute(Language aLanguage) {

        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            pm.makePersistent(aLanguage);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return aLanguage;
    }
}
