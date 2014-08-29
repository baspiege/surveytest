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
 * Question text which is identified by survey Id, question Id, and language Id.
 *
 * @author Brian Spiegel
 */
public class QuestionText implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String text;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private long questionId;
    
    @Persistent
    private long languageId;

    // This does not need to be persisted.
    private Language language;

    public QuestionText()
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
    
    public long getQuestionId() {
        return questionId;
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
    
    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
    }
    
    public void setSurveyId(long aSurveyId) {
        surveyId=aSurveyId;
    }
}