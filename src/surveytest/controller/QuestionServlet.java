package surveytest.controller;

import surveytest.data.model.Question;
import surveytest.utils.RequestUtils;
import java.io.IOException;
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
        Question question=null;

        if (question==null) {
            throw new RuntimeException("Question not found: " + questionId);
        } else {
            request.setAttribute(RequestUtils.QUESTION,question);
        }

    }
}