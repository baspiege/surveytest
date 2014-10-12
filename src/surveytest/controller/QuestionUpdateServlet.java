package surveytest.controller;

import surveytest.data.AnswerSetGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionDelete;
import surveytest.data.QuestionGetSingle;
import surveytest.data.QuestionUpdate;
import surveytest.data.QuestionTextAdd;
import surveytest.data.QuestionTextDelete;
import surveytest.data.QuestionTextGetAll;
import surveytest.data.QuestionTextUpdate;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
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

/**
* Process question updates.
*/
public class QuestionUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
    }

    /**
    * Update or delete question.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");       
                
        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("updateLabel"))) {		
                // Fields
                String description=RequestUtils.getAlphaInput(request,"description",bundle.getString("descriptionLabel"),true);
                question.setText(description);
                
                Long answerSetId=RequestUtils.getNumericInput(request,"answerSetId",bundle.getString("answerSetLabel"),true);
                question.setAnswerSetId(answerSetId);
                
                // Question Text
                List<QuestionText> questionTexts=(List<QuestionText>)request.getAttribute(RequestUtils.QUESTION_TEXTS);
                for (QuestionText questionText: questionTexts) {
                    String questionTextLanguageId="questionText_Language_" + questionText.getLanguageId();
                    String questionTextLanguage=RequestUtils.getAlphaInput(request,questionTextLanguageId,questionText.getLanguage().getName(),true);
                    questionText.setText(questionTextLanguage);
                }
                
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {		
                deleteAction(request,response);
            }
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<QuestionText> questionTexts=(List<QuestionText>)request.getAttribute(RequestUtils.QUESTION_TEXTS);
        
        if (!EditUtils.hasEdits(request)) {
            question.setLastUpdateUserId(request.getUserPrincipal().getName());
            question=QuestionUpdate.execute(question);
            
            for (QuestionText questionText: questionTexts) {
                questionText.setLastUpdateUserId(request.getUserPrincipal().getName());
                if (questionText.getQuestionId()>0) {
                    QuestionTextUpdate.execute(questionText);
                } else {
                    questionText.setQuestionId(question.getKey().getId());
                    QuestionTextAdd.execute(questionText);                
                }
            }
        }
        // If no edits, forward to question.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("questionId",question.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
        }
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<QuestionText> questionTexts=(List<QuestionText>)request.getAttribute(RequestUtils.QUESTION_TEXTS);
      
        if (!EditUtils.hasEdits(request)) {
            question.setLastUpdateUserId(request.getUserPrincipal().getName());
            QuestionDelete.execute(question);
            
            for (QuestionText questionText: questionTexts) {
                questionText.setLastUpdateUserId(request.getUserPrincipal().getName());
                QuestionTextDelete.execute(questionText);
            }
        }
        
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",question.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        if (!UserUtils.isLoggedOn(request)) {
            throw new UserNotFoundException();
        }
        
        Long questionId=RequestUtils.getNumericInput(request,"questionId","questionId",true);
        Question question=QuestionGetSingle.execute(questionId);
        if (question==null) {
            throw new RuntimeException("Question not found: " + questionId);
        } else {
            request.setAttribute(RequestUtils.QUESTION,question);
        }
        
        Survey survey=SurveyGetSingle.execute(question.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);

        List<Language> languages=LanguageGetAll.execute(survey.getKey().getId());
        request.setAttribute(RequestUtils.LANGUAGES, languages);
                
        List<QuestionText> questionTexts=QuestionTextGetAll.execute(questionId);
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);

        Map questionTextMap=new HashMap();
        for (QuestionText questionText: questionTexts) {
            questionTextMap.put(questionText.getLanguageId(), questionText);
        }
        
        for (Language language: languages) {
            if (!questionTextMap.containsKey(language.getKey().getId())) {
                QuestionText questionText=new QuestionText();
                questionText.setSurveyId(survey.getKey().getId());
                questionText.setQuestionId(question.getKey().getId());
                questionText.setLanguage(language);
                questionText.setLanguageId(language.getKey().getId());
                questionText.setText("");
                questionTexts.add(questionText);
            } else { 
                QuestionText questionText=(QuestionText)questionTextMap.get(language.getKey().getId());
                questionText.setLanguage(language);
            }
        }
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
        
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(survey.getKey().getId());
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
    }
}