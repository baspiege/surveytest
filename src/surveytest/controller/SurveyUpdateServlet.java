package surveytest.controller;

import surveytest.data.AnswerSetGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionGetAll;
import surveytest.data.SurveyDelete;
import surveytest.data.SurveyGetSingle;
import surveytest.data.SurveyUpdate;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.Survey;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import surveytest.utils.UserUtils;
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

        survey.setLastUpdateUserId(request.getUserPrincipal().getName());
        survey=SurveyUpdate.execute(survey);
        
        // If no edits, forward to question.
        if (!EditUtils.hasEdits(request)) {
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
            EditUtils.addEditUsingKey(request,"surveyCantBeDeletedWithQuestionsMessage");
        }
        
        if (!languages.isEmpty()) {
            EditUtils.addEditUsingKey(request,"surveyCantBeDeletedWithLanguagesMessage");
        }
        
        if (!answerSets.isEmpty()) {
            EditUtils.addEditUsingKey(request,"surveyCantBeDeletedWithAnswerSetsMessage");
        }
      
        if (!EditUtils.hasEdits(request)) {
            survey.setLastUpdateUserId(request.getUserPrincipal().getName());
            SurveyDelete.execute(survey);
        }
        
        if (!EditUtils.hasEdits(request)) {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        if (!UserUtils.isLoggedOn(request)) {
            throw new UserNotFoundException();
        }

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
        
        List<Language> languages=LanguageGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
        
        List<Question> questions=QuestionGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.QUESTIONS, questions);
        
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
    }
}