package surveytest.controller;

import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Survey;
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

public class SurveyUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_UPDATE);
    }

    /**
    * Update or delete survey.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");       
                
        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("updateLabel"))) {		

                // Fields
                String name=RequestUtils.getAlphaInput(request,"name",bundle.getString("nameLabel"),true);
                survey.setName(name);
                
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {            
                deleteAction(request,response);
            }
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);

        // If no edits, forward to question.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("surveyId",survey.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_UPDATE);
        }
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
      
        List<Question> questions=(List<Question>)request.getAttribute(RequestUtils.QUESTIONS);
        List<Language> languages=(List<Language>)request.getAttribute(RequestUtils.LANGUAGES);
        List<AnswerSet> answerSets=(List<AnswerSet>)request.getAttribute(RequestUtils.ANSWER_SETS);        
      
        if (!questions.isEmpty()) {
            RequestUtils.addEditUsingKey(request,"surveyCantBeDeletedWithQuestionsMessage");
        }
        
        if (!languages.isEmpty()) {
            RequestUtils.addEditUsingKey(request,"surveyCantBeDeletedWithLanguagesMessage");
        }
        
        if (!answerSets.isEmpty()) {
            RequestUtils.addEditUsingKey(request,"surveyCantBeDeletedWithAnswerSetsMessage");
        }
      
        if (!RequestUtils.hasEdits(request)) {
            SurveyDelete.execute(survey);
        }
        
        if (!RequestUtils.hasEdits(request)) {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_UPDATE);
        }
    }

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
        
        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
        
        // Get questions
        List<Question> questions=QuestionGetAll.execute(surveyId, null);
        request.setAttribute(RequestUtils.QUESTIONS, questions);
        
        // Get answers sets
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(surveyId, null);
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
    }
}