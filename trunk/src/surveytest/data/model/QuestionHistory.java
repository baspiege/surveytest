package surveytest.data.model;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import surveytest.utils.NumberUtils;

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
    private String lastReview;	

    @Persistent
    private Date lastUpdateTime;	

    @Persistent
    private String note;

    @Persistent
    private long reviewCount;

    @Persistent
    private long storeId;

    @Persistent
    private String user;

    @Persistent
    private long yesVote;

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
        setNote(aQuestion.getNote());
        setLastUpdateTime(aQuestion.getLastUpdateTime());
        setStoreId(aQuestion.getStoreId());
        setYesVote(aQuestion.getYesVote());
        setUser(aQuestion.getUser());
    }

    public Key getKey() {
        return key;
    }

    public void setQuestionId(long aQuestionId) {
        questionId=aQuestionId;
    }


    public void setNote(String aNote) {
        note=aNote;
    }

    public void setLastReview(String aLastReview) {
        lastReview=aLastReview;
    }

    public void setLastUpdateTime(Date aLastUpdateTime) {
        lastUpdateTime=aLastUpdateTime;
    }

    public void setReviewCount(long aReviewCount) {
        reviewCount=aReviewCount;
    }

    public void setStoreId(long aStoreId) {
        storeId=aStoreId;
    }

    public void setUser(String aUser) {
        user=aUser;
    }

    public void setYesVote(long aYes) {
        yesVote=aYes;
    }
}