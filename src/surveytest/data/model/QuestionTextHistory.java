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

public class QuestionTextHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String text;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private long questionTextId;
    
    @Persistent
    private long questionId;
    
    @Persistent
    private long languageId;
    
    @Persistent
    private Date lastUpdateTime;
    
    @Persistent
    private String lastUpdateUserId;
    
    @Persistent
    private String action;

    public QuestionTextHistory(QuestionText aQuestionText, String aAction) {
        setSurveyId(aQuestionText.getSurveyId());
        setLanguageId(aQuestionText.getLanguageId());
        setQuestionId(aQuestionText.getQuestionId());
        setQuestionTextId(aQuestionText.getKey().getId());
        setText(aQuestionText.getText());
        setLastUpdateTime(aQuestionText.getLastUpdateTime());
        setLastUpdateUserId(aQuestionText.getLastUpdateUserId());
        setAction(aAction);
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
        
    public long getLanguageId() {
        return languageId;
    }
    
    public long getQuestionId() {
        return questionId;
    }
    
    public long getSurveyId() {
        return surveyId;
    }
       
    public void setLanguageId(long aLanguageId) {
        languageId=aLanguageId;
    }
    
    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
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
    
    public String getAction() {
        return action;
    }

    public void setAction(String aAction) {
        action=aAction;
    } 
    
    public long getQuestionTexId() {
        return questionTextId;
    }
    
    public void setQuestionTextId(long aQuestionTextId) {
        questionTextId=aQuestionTextId;
    }
}