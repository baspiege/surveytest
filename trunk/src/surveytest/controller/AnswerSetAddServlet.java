package surveytest.controller;

import surveytest.data.AnswerSetAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Survey;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        // Default
        Question question=(Question)request.getAttribute(RequestUtils.ANSWER_SET);
        question.setText("");
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_ADD);
    }

    /**
    * Add.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<Language> languages=(List<Language>)request.getAttribute(RequestUtils.LANGUAGES);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String note=RequestUtils.getAlphaInput(request,"note",bundle.getString("noteLabel"),true);
                question.setText(note);

                if (!RequestUtils.hasEdits(request)) {
                    question=QuestionAdd.execute(question);
                }
            }
        }

        // If no edits, forward to question.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("surveyId",question.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_ADD);
        }
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

        // Check if signed in
//        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
//        if (!isSignedIn) {
//            throw new SecurityException("User principal not found");
//        }

        // Check survey
        Long surveyId=RequestUtils.getNumericInput(request,"surveyId","surveyId",true);
        Survey survey=null;
        if (surveyId!=null) {
            survey=SurveyGetSingle.execute(surveyId);
            request.setAttribute(RequestUtils.SURVEY, survey);
        }
        if (survey==null) {
            throw new RuntimeException("Survey not found:" + surveyId);
        }

        // Answer set
        AnswerSet answerSet=new AnswerSet();
        answerSet.setSurveyId(survey.getKey().getId());
        //question.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.ANSWER_SET, answerSet);

    }
}