package surveytest.controller;

import surveytest.data.model.Answer;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Survey;
import surveytest.data.AnswerSetGetSingle;
import surveytest.data.AnswerGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.RequestUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerSetServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET);
    }

    /**
    * No post for now.  Route to main page.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

        // Get AnswerSet
        Long answerSetId=RequestUtils.getNumericInput(request,"answerSetId","answerSetId",true);
        AnswerSet answerSet=AnswerSetGetSingle.execute(answerSetId);

        if (answerSet==null) {
            throw new RuntimeException("AnswerSet not found: " + answerSetId);
        } else {
            request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
        }
        
        List<Answer> answers=AnswerGetAll.execute(answerSetId, 0L, null);
        request.setAttribute(RequestUtils.ANSWERS, answers);
        
        Survey survey=SurveyGetSingle.execute(answerSet.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);
    }
}