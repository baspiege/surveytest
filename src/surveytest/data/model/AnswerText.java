package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

/**
 * Answer text which is identified by survey Id, answer set Id, and language Id.
 */
public class AnswerText implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String text;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private long answerSetId;
    
    @Persistent
    private long answerId;
    
    @Persistent
    private long languageId;
    
    @Persistent
    private Date lastUpdateTime;
    
    @Persistent
    private String lastUpdateUserId;

    @NotPersistent
    private Language language;

    public AnswerText()
    {
    }

    public Key getKey() {
        return key;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String aText) {
        text=aText;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public long getLanguageId() {
        return languageId;
    }
    
    public long getAnswerId() {
        return answerId;
    }
    
    public long getAnswerSetId() {
        return answerSetId;
    }
    
    public long getSurveyId() {
        return surveyId;
    }
   
    public void setLanguage(Language aLanguage) {
        language=aLanguage;
    }
    
    public void setLanguageId(long aLanguageId) {
        languageId=aLanguageId;
    }
    
    public void setAnswerId(long aAnswerId) {
        answerId=aAnswerId;
    }
    
    public void setAnswerSetId(long aAnswerSetId) {
        answerSetId=aAnswerSetId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
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