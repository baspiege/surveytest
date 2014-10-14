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

public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private long surveyId;
    
    @Persistent
    private String identifier;
    
    @Persistent
    private long surveyResponseId;
    
    @Persistent
    private boolean used;
    
    @Persistent
    private String url;
    
    @Persistent
    private Date lastUpdateTime;
    
    @Persistent
    private String lastUpdateUserId;
    
    public Reward() {
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
    
    public long getResponseSurveyId() {
        return surveyResponseId;
    }
    
    public void setSurveyResponseId(long aSurveyResponseId) {
        surveyResponseId=aSurveyResponseId;
    }
   
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String aIdentifier) {
        identifier=aIdentifier;
    }
    
    public boolean getUsed() {
        return used;
    }
    
    public void setUsed(boolean aUsed) {
        used=aUsed;
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
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String aUrl) {
        url=aUrl;
    }

}