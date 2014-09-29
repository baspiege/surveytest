package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

public class QuestionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private long surveyResponseId;
    
    @Persistent
    private long questionId;    
    
    @Persistent
    private String questionText;
    
    @Persistent
    private long answerId;
    
    @Persistent
    private String answerText;
    
    @Persistent
    private Date lastUpdateTime;

    public QuestionResponse()
    {
    }

    public Key getKey() {
        return key;
    }
    
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String aAnswerText) {
        answerText=aAnswerText;
    }
    
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String aQuestionText) {
        questionText=aQuestionText;
    }
    
    public long getResponseSurveyId() {
        return surveyResponseId;
    }
    
    public void setSurveyResponseId(long aSurveyResponseId) {
        surveyResponseId=aSurveyResponseId;
    }
    
    public long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
    
    public long getAnswerId() {
        return answerId;
    }
    
    public void setAnswerId(long aAnswerId) {
        answerId=aAnswerId;
    }  
    
    public long getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
    }      
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date aLastUpdateTime) {
        lastUpdateTime=aLastUpdateTime;
    }

}