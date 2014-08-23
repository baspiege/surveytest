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
 * Question history.
 *
 * @author Brian Spiegel
 */
public class QuestionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private long questionId;

    @Persistent
    private String text;

    /**
     * Constructor.
     *
     */
    public QuestionHistory() {
    }

    /**
     * Constructor.
     *
     */
    public QuestionHistory(Question aQuestion) {
        setQuestionId(aQuestion.getKey().getId());
        setText(aQuestion.getText());
    }

    public Key getKey() {
        return key;
    }

    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
    }

    public void setText(String aText) {
        text=aText;
    }

}