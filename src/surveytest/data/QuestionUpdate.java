package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionHistory;

public class QuestionUpdate {

    public static Question execute(Question aQuestion) {

        Question question=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            question=QuestionGetSingle.getQuestion(pm,aQuestion.getKey().getId());

            if (question!=null){

                if (aQuestion.getText()!=null) {
                    question.setText(aQuestion.getText());
                }
                
                question.setAnswerSetId(aQuestion.getAnswerSetId());
                question.setLastUpdateTime(new Date());
                question.setLastUpdateUserId(aQuestion.getLastUpdateUserId());

                QuestionHistory questionHistory=new QuestionHistory(question, DataConstants.UPDATE);
                pm.makePersistent(questionHistory);
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return question;
    }
}
