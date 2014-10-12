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

public class QuestionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private long questionId;

    @Persistent
    private String text;
    
    @Persistent
    private String textLowerCase;
    
    @Persistent
    private long surveyId;    
    
    @Persistent
    private long answerSetId;
    
    @Persistent
    private Date lastUpdateTime;
    
    @Persistent
    private String lastUpdateUserId;
    
    @Persistent
    private String action;

    public QuestionHistory() {
    }

    public QuestionHistory(Question aQuestion, String aAction) {
        setQuestionId(aQuestion.getKey().getId());
        setText(aQuestion.getText());
        setSurveyId(aQuestion.getSurveyId());
        setAnswerSetId(aQuestion.getAnswerSetId());
        setLastUpdateTime(aQuestion.getLastUpdateTime());
        setLastUpdateUserId(aQuestion.getLastUpdateUserId());
        setAction(aAction);
    }

    public Key getKey() {
        return key;
    }

    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
    }

    public void setText(String aText) {
        text=aText;
        textLowerCase=aText.toLowerCase();
    }
    
    public long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
    
    public long getAnswerSetId() {
        return answerSetId;
    }
    
    public void setAnswerSetId(long aAnswerSetId) {
        answerSetId=aAnswerSetId;
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
    
}