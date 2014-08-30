package surveytest.controller;

import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
import surveytest.data.QuestionGetSingle;
import surveytest.data.QuestionTextGetAll;
import surveytest.utils.RequestUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Show question.
*/
public class QuestionServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION);
    }

    /**
    * No post for now.  Route to main page.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

        // Get question
        Long questionId=RequestUtils.getNumericInput(request,"questionId","questionId",true);
        Question question=QuestionGetSingle.execute(questionId);

        if (question==null) {
            throw new RuntimeException("Question not found: " + questionId);
        } else {
            request.setAttribute(RequestUtils.QUESTION,question);
        }
        
        List<QuestionText> questionTexts=QuestionTextGetAll.execute(questionId, 0L, null);
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);

    }
}