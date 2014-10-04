package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

public class SurveyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private long languageId;
    
    @Persistent
    private Date lastUpdateTime;	

    @NotPersistent
    private List<QuestionResponse> questionResponses=new ArrayList<QuestionResponse>();    
    
    public SurveyResponse()
    {
    }

    public Key getKey() {
        return key;
    }
        
    public long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
    
    public long getLanguageId() {
        return languageId;
    }
    
    public void setLanguageId(long aLanguageId) {
        languageId=aLanguageId;
    }
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date aLastUpdateTime) {
        lastUpdateTime=aLastUpdateTime;
    }
    
    public List<QuestionResponse> getQuestionResponses() {
        return questionResponses;
    }
    
    public void setQuestionResponses(List<QuestionResponse> aQuestionResponses) {
        questionResponses=aQuestionResponses;
    } 
}