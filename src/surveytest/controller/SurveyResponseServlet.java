package surveytest.controller;

import surveytest.data.model.Answer;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.AnswerText;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
import surveytest.data.model.Survey;
import surveytest.data.AnswerGetAll;
import surveytest.data.AnswerSetGetAll;
import surveytest.data.AnswerTextGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.LanguageGetSingle;
import surveytest.data.QuestionGetAll;
import surveytest.data.QuestionTextGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.RequestUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveyResponseServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSE);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO - Process
    
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

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
        
        Long languageId=RequestUtils.getNumericInput(request,"languageId","languageId",true);
        Language selectedLanguage=null;
        if (languageId!=null) {
            selectedLanguage=LanguageGetSingle.execute(languageId);
            request.setAttribute(RequestUtils.LANGUAGE, selectedLanguage);
        }
        if (selectedLanguage==null) {
            throw new RuntimeException("Language not found:" + languageId);
        }
                
        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
        
        // Get questions
        List<Question> questions=QuestionGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.QUESTIONS, questions);
        
        // Get question texts
        List<QuestionText> questionTexts=QuestionTextGetAll.executeBySurveyId(surveyId, 0L, null);
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
                
        // Get answers sets
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
        
        // Get answers
        List<Answer> answers=AnswerGetAll.executeBySurveyId(surveyId, 0L, null);
        request.setAttribute(RequestUtils.ANSWERS, answers);
                
        // Get answer texts
        List<AnswerText> answerTexts=AnswerTextGetAll.executeBySurveyId(surveyId, 0L, null);
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);
                
        // Answer set map       
        Map answerSetMap=new HashMap();
        for (AnswerSet answerSet: answerSets) {
            answerSetMap.put(answerSet.getKey().getId(), answerSet);
        }                
        
        // Language map
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
                
        // Link answer text to language
        for (AnswerText answerText: answerTexts) {
            if (languagesMap.containsKey(answerText.getLanguageId())) {
                Language language=(Language)languagesMap.get(answerText.getLanguageId());
                answerText.setLanguage(language);
            }
        }
        
        // Link question text to language
        for (QuestionText questionText: questionTexts) {
            if (languagesMap.containsKey(questionText.getLanguageId())) {
                Language language=(Language)languagesMap.get(questionText.getLanguageId());
                questionText.setLanguage(language);
            }
            for (Question question: questions) {
                if (question.getKey().getId()==questionText.getQuestionId()) {
                    question.getQuestionTextMap().put(questionText.getLanguageId(),questionText);
                }
            }
        }
        
        // Link answer set to question
        for (Question question: questions) {
            AnswerSet answerSet=null;
            if (answerSetMap.containsKey(question.getAnswerSetId())) {
                answerSet=(AnswerSet)answerSetMap.get(question.getAnswerSetId());
            } else {
                answerSet=new AnswerSet();
            }
            question.setAnswerSet(answerSet);
        }
    }
}