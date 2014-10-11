package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
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
    
    @Persistent
    private Date lastUpdateTime;
    
    @Persistent
    private String lastUpdateUserId;
    
    @NotPersistent
    private List<Answer> answers=new ArrayList<Answer>();

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
    
    public List<Answer> getAnswers() {
        return answers;
    }
    
    public void setAnswer(List<Answer> aAnswers) {
        answers=aAnswers;
    } 
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date aLastUpdateTime) {
        lastUpdateTime=aLastUpdateTime;
    }
    
    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String aLastUpdateUserId) {
        lastUpdateUserId=aLastUpdateUserId;
    } 
}