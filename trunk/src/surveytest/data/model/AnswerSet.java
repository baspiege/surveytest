package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

public class AnswerSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String description;
    
    @Persistent
    private String descriptionLowerCase;
    
    @Persistent
    private long surveyId;

    /**
     * Constructor.
     *
     */
    public AnswerSet()
    {
    }

    public Key getKey() {
        return key;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDescriptionLowerCase() {
        return descriptionLowerCase;
    }

    public void setDescription(String aDescription) {
        description=aDescription;
        descriptionLowerCase=aDescription.toLowerCase();
    }
    
    public long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
}