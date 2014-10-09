package surveytest.controller;

import surveytest.data.AnswerGetAll;
import surveytest.data.AnswerSetDelete;
import surveytest.data.AnswerSetGetSingle;
import surveytest.data.AnswerSetUpdate;
import surveytest.data.LanguageGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Answer;
import surveytest.data.model.Survey;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerSetUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_UPDATE);
    }

    /**
    * Update or delete answer set.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");       
                
        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("updateLabel"))) {		
                // Fields
                String description=RequestUtils.getAlphaInput(request,"description",bundle.getString("descriptionLabel"),true);
                answerSet.setDescription(description);
                               
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {		
                deleteAction(request,response);
            }
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);
        
        if (!EditUtils.hasEdits(request)) {
            answerSet=AnswerSetUpdate.execute(answerSet);
        }
        // If no edits, forward to answer set.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("answerSetId",answerSet.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_UPDATE);
        }
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);
      
        if (!EditUtils.hasEdits(request)) {
            AnswerSetDelete.execute(answerSet);
        }
        
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId", answerSet.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        // Check if signed in
//        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
//        if (!isSignedIn) {
//            throw new SecurityException("User principal not found");
//        }

        // Get answer set
        Long answerSetId=RequestUtils.getNumericInput(request,"answerSetId","answerSetId",true);
        AnswerSet answerSet=AnswerSetGetSingle.execute(answerSetId);
        if (answerSet==null) {
            throw new RuntimeException("AnswerSet not found: " + answerSetId);
        } else {
            request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
        }
        
        // Survey
        Survey survey=SurveyGetSingle.execute(answerSet.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);
        
        List<Answer> answers=AnswerGetAll.execute(answerSetId);
        request.setAttribute(RequestUtils.ANSWERS, answers);
    }
}