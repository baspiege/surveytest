package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;
    
    @Persistent
    private String nameLowerCase;
    
    @Persistent
    private String introText;
    
    @Persistent
    private String confirmationText;
    
    @Persistent
    private String identifierText;
    
    @Persistent
    private String submitButtonText;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private String surveyName;
    
    @Persistent
    private Date lastUpdateTime;	

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
        nameLowerCase=aName.toLowerCase();
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
    
    public String getIntroText() {
        return introText;
    }
    
    public void setIntroText(String aIntroText) {
        introText=aIntroText;
    }
    
    public String getConfirmationText() {
        return confirmationText;
    }
    
    public void setConfirmationText(String aConfirmationText) {
        confirmationText=aConfirmationText;
    }
    
    public String getIdentifierText() {
        return identifierText;
    }
    
    public void setIdentifierText(String aIdentifierText) {
        identifierText=aIdentifierText;
    }
    
    public String getSubmitButtonText() {
        return submitButtonText;
    }
    
    public void setSubmitButtonText(String aSubmitButtonText) {
        submitButtonText=aSubmitButtonText;
    }
    
    public String getSurveyName() {
        return surveyName;
    }
    
    public void setSurveyName(String aSurveyName) {
        surveyName=aSurveyName;
    }
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date aLastUpdateTime) {
        lastUpdateTime=aLastUpdateTime;
    }
}