package surveytest.data;

import java.util.Date;
import java.util.Map;
import javax.jdo.PersistenceManager;

import surveytest.data.model.QuestionText;

/**
 * Update a questionText.
 *
 * @author Brian Spiegel
 */
public class QuestionTextUpdate {

    /**
     * Update a questionText.
     *
     * @param aQuestionText questionText
     * @return an updated questionText
     *
     * @since 1.0
     */
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

                // questionText.setLastUpdateTime(new Date());
                // questionText.setUser(aQuestionText.getUser());
            }
        } finally {
            if (pm!=null) {
                pm.close();
            }
        }
        return questionText;
    }
}
