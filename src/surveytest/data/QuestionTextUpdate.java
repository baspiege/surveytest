package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.QuestionText;
import surveytest.data.model.QuestionTextHistory;

public class QuestionTextUpdate {

    public static QuestionText execute(QuestionText aQuestionText) {

        QuestionText questionText=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            questionText=QuestionTextGetSingle.getQuestionText(pm,aQuestionText.getKey().getId());

            if (questionText!=null){

                if (aQuestionText.getText()!=null) {
                    questionText.setText(aQuestionText.getText());
                }

                questionText.setLastUpdateTime(new Date());
                questionText.setLastUpdateUserId(aQuestionText.getLastUpdateUserId());
                
                QuestionTextHistory questionTextHistory=new QuestionTextHistory(questionText, DataConstants.UPDATE);
                pm.makePersistent(questionTextHistory);
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return questionText;
    }
}
