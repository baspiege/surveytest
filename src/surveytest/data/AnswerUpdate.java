package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Answer;

public class AnswerUpdate {

    public static Answer execute(Answer aAnswer) {

        Answer answer=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswer might be transient.
            answer=AnswerGetSingle.getAnswer(pm,aAnswer.getKey().getId());

            if (answer!=null){

                if (aAnswer.getText()!=null) {
                    answer.setText(aAnswer.getText());
                }

                // answer.setLastUpdateTime(new Date());
                // answer.setUser(aAnswer.getUser());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answer;
    }
}
