package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionHistory;

/**
 * Update a question.
 *
 * @author Brian Spiegel
 */
public class QuestionUpdate {

    /**
     * Update a question.
     *
     * @param aQuestion question
     * @return an updated question
     *
     * @since 1.0
     */
    public static Question execute(Question aQuestion) {

        Question question=null;
        PersistenceManager pm=null;
        try {
            pm=PMF.get().getPersistenceManager();

            question=QuestionGetSingle.getQuestion(pm,aQuestion.getKey().getId());

            if (question!=null){

                if (aQuestion.getNote()!=null) {
                    question.setNote(aQuestion.getNote());
                }

                question.setLastUpdateTime(new Date());
                question.setUser(aQuestion.getUser());

                // History
                QuestionHistory questionHistory=new QuestionHistory(question);
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
