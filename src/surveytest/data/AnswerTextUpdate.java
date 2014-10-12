package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.AnswerText;

public class AnswerTextUpdate {

    public static AnswerText execute(AnswerText aAnswerText) {

        AnswerText answerText=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            // Get managed instance.  aAnswerText might be transient.
            answerText=AnswerTextGetSingle.getAnswerText(pm,aAnswerText.getKey().getId());

            if (answerText!=null){

                if (aAnswerText.getText()!=null) {
                    answerText.setText(aAnswerText.getText());
                }

                answerText.setLastUpdateTime(new Date());
                answerText.setLastUpdateUserId(aAnswerText.getLastUpdateUserId());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return answerText;
    }
}
