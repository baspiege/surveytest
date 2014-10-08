package surveytest.data;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Singleton to get PersistenceManagerFactory.
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {
        // Do nothing
    }

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
