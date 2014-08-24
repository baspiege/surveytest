package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

/**
 * Language.
 *
 * @author Brian Spiegel
 */
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;	
    
    @Persistent
    private long surveyId;

    /**
     * Constructor.
     *
     */
    public Language()
    {
    }

    public Key getKey() {
        return key;
    }
    
    public String getName() {
        return name;
    }
    
    public long getSurveyId() {
        return surveyId;
    }

    public void setName(String aName) {
        name=aName;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
}