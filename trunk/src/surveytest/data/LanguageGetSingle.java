package surveytest.data;

import surveytest.data.model.Language;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class LanguageGetSingle {

    public static Language execute(Long aLanguageId) {
        PersistenceManager pm=null;
        Language language=null;
        try {
            pm=PMF.get().getPersistenceManager();
            language=LanguageGetSingle.getLanguage(pm,aLanguageId);
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return language;
    }

    /**
     * Get a language.
     *
     * @param aPm PersistenceManager
     * @param aLanguageId language Id
     * @return a language null if not found
     *
     * @since 1.0
     */
    public static Language getLanguage(PersistenceManager aPm, long aLanguageId) {
        return aPm.getObjectById(Language.class, aLanguageId);
    }
}
