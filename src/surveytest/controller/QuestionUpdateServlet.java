package surveytest.controller;

import surveytest.data.AnswerSetGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionDelete;
import surveytest.data.QuestionUpdate;
import surveytest.data.QuestionTextAdd;
import surveytest.data.QuestionTextDelete;
import surveytest.data.QuestionTextUpdate;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
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

    /**
    * Update action.
    */
    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<QuestionText> questionTexts=(List<QuestionText>)request.getAttribute(RequestUtils.QUESTION_TEXTS);
        
        if (!RequestUtils.hasEdits(request)) {
            question=QuestionUpdate.execute(question);
            
            for (QuestionText questionText: questionTexts) {
                if (questionText.getQuestionId()>0) {
                    QuestionTextUpdate.execute(questionText);
                } else {
                    questionText.setQuestionId(question.getKey().getId());
                    QuestionTextAdd.execute(questionText);                
                }
            }
        }
        // If no edits, forward to question.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("questionId",question.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
        }
    }

    /**
    * Delete action.
    */
    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<QuestionText> questionTexts=(List<QuestionText>)request.getAttribute(RequestUtils.QUESTION_TEXTS);
      
        if (!RequestUtils.hasEdits(request)) {
            QuestionDelete.execute(question);
            
            for (QuestionText questionText: questionTexts) {
                QuestionTextDelete.execute(questionText);
            }
        }
        
        if (!RequestUtils.hasEdits(request)) {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
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

        // Set question
        Question question=new Question();
        question.setSurveyId(survey.getKey().getId());
        //question.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.QUESTION, question);

        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        // Question Texts
        List<QuestionText> questionTexts=new ArrayList<QuestionText>();
        for (Language language: languages) {
            QuestionText questionText=new QuestionText();
            questionText.setLanguage(language);
            questionText.setLanguageId(language.getKey().getId());
            questionText.setText("");
            questionTexts.add(questionText);
        }
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
        
        // Get answers sets
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
    }
}