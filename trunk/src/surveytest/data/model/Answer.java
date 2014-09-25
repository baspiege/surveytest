package surveytest.data.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")

public class Answer implements Serializable {

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
    
    @NotPersistent
    private Map<Long,AnswerText> answerTextMap=new HashMap<Long,AnswerText>();

    /**
     * Constructor.
     *
     */
    public Answer()
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
    
    public Map<Long,AnswerText> getAnswerTextMap() {
        return answerTextMap;
    }
    
    public void setAnswerTextMap(Map<Long,AnswerText> aAnswerTextMap) {
        answerTextMap=aAnswerTextMap;
    } 

}