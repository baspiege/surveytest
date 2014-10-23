package surveytest.controller;

import surveytest.data.model.Answer;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.AnswerText;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionResponse;
import surveytest.data.model.QuestionText;
import surveytest.data.model.Reward;
import surveytest.data.model.Survey;
import surveytest.data.model.SurveyResponse;
import surveytest.data.AnswerGetAll;
import surveytest.data.AnswerSetGetAll;
import surveytest.data.AnswerTextGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.LanguageGetSingle;
import surveytest.data.QuestionGetAll;
import surveytest.data.QuestionResponseAdd;
import surveytest.data.QuestionTextGetAll;
import surveytest.data.RewardGetSingle;
import surveytest.data.SurveyGetSingle;
import surveytest.data.SurveyResponseAdd;
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

public class SurveyResponseServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSE);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setUpData(request);
        
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
   
        SurveyResponse surveyResponse=(SurveyResponse)request.getAttribute(RequestUtils.SURVEY_RESPONSE);
        
        List<Question> questions=(List<Question>)request.getAttribute(RequestUtils.QUESTIONS);
        List<Answer> answers=(List<Answer>)request.getAttribute(RequestUtils.ANSWERS);
        Language language=(Language)request.getAttribute(RequestUtils.LANGUAGE);
        
        Map answerMap=new HashMap();
        for (Answer answer: answers) {
            answerMap.put(answer.getKey().getId(), answer);
        }                
                
        Map questionMap=new HashMap();
        for (Question question: questions) {
            questionMap.put(question.getKey().getId(), question);
        }

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(language.getSubmitButtonText())) {		
            
                String identifier=RequestUtils.getAlphaInput(request,"identifier",language.getIdentifierText(),true);
                surveyResponse.setIdentifier(identifier);
            
                List<QuestionResponse> questionResponses=new ArrayList<QuestionResponse>();
                Map<Long, QuestionResponse> questionResponsesMap=new HashMap<Long, QuestionResponse>();
            
                // Fields
                Map<String,String[]> parameterMap=request.getParameterMap();                
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String parameter = entry.getKey();
                    String[] values = entry.getValue();
                    
                    if (parameter.startsWith("question_")) {
                        String[] parameterParts=parameter.split("_");
                        long questionId=new Long(parameterParts[1]);
                        long answerId=new Long(values[0]);
                        
                        Question question=(Question)questionMap.get(questionId);
                        Answer answer=(Answer)answerMap.get(answerId);
                        
                        QuestionResponse questionResponse=new QuestionResponse();
                        questionResponse.setQuestionId(questionId);
                        questionResponse.setQuestionText(question.getText());
                        questionResponse.setAnswerId(answerId);
                        questionResponse.setAnswerText(answer.getText());
                        questionResponse.setSurveyId(surveyResponse.getSurveyId());
                        questionResponses.add(questionResponse);
                        questionResponsesMap.put(questionId, questionResponse);
                    }
                }
                
                // Verify each question is answered
                for (Question question: questions) {
                    if (!questionResponsesMap.containsKey(question.getKey().getId())) {
                        QuestionText questionText=(QuestionText)question.getQuestionTextMap().get(language.getKey().getId());
                        String required=bundle.getString("fieldRequiredEdit");
                        EditUtils.addEdit(request, questionText.getText() + ": " + required);  // TODO - Make required variable by language
                    }
                }
                
                if (!EditUtils.hasEdits(request)) {
                    surveyResponse=SurveyResponseAdd.execute(surveyResponse);
                    
                    // Save all the question responses
                    for (QuestionResponse questionResponse: questionResponses) {
                        questionResponse.setSurveyResponseId(surveyResponse.getKey().getId());
                        QuestionResponseAdd.execute(questionResponse);
                    }
                }
            }
        }

        // If no edits, forward to success page.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",surveyResponse.getSurveyId());
            request.setAttribute("languageId",surveyResponse.getLanguageId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_CONFIRMATION_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSE);
        }
    
    }

    private void setUpData(HttpServletRequest request) {
    
        Long rewardId=RequestUtils.getNumericInput(request,"rewardId","rewardId",false);
        Reward reward=null;
        if (rewardId!=null) {
            reward=RewardGetSingle.execute(rewardId);
            request.setAttribute(RequestUtils.REWARD, reward);
        }
        
        // Check if reward token matches
        if (reward!=null) {
            Long tokenId=RequestUtils.getNumericInput(request,"tokenId","tokenId",false);
            if (reward.getToken()!=tokenId) {
                String message="Inputted token not valid for reward id: " + reward.getKey().getId()
                    + " reward token: " + reward.getToken()
                    + " inputted token: " + tokenId;
                throw new RuntimeException(message);
            }
            if (reward.getUsed()) {
                String message="Reward already used";
                throw new RuntimeException(message);
            }
        }
    
        SurveyResponse surveyResponse=new SurveyResponse();
        request.setAttribute(RequestUtils.SURVEY_RESPONSE, surveyResponse);
            
        Long surveyId=RequestUtils.getNumericInput(request,"surveyId","surveyId",true);
        Survey survey=null;
        if (surveyId!=null) {
            survey=SurveyGetSingle.execute(surveyId);
            request.setAttribute(RequestUtils.SURVEY, survey);
            surveyResponse.setSurveyId(surveyId);
        }
        if (survey==null) {
            throw new RuntimeException("Survey not found:" + surveyId);
        }

        Long languageId=RequestUtils.getNumericInput(request,"languageId","languageId",true);
        Language selectedLanguage=null;
        if (languageId!=null) {
            selectedLanguage=LanguageGetSingle.execute(languageId);
            request.setAttribute(RequestUtils.LANGUAGE, selectedLanguage);
            surveyResponse.setLanguageId(languageId);
        }
        if (selectedLanguage==null) {
            throw new RuntimeException("Language not found:" + languageId);
        }
 
        List<Language> languages=LanguageGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
        
        List<Question> questions=QuestionGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.QUESTIONS, questions);
        
        List<QuestionText> questionTexts=QuestionTextGetAll.executeBySurveyId(surveyId);
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
                
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
        
        List<Answer> answers=AnswerGetAll.executeBySurveyId(surveyId);
        request.setAttribute(RequestUtils.ANSWERS, answers);
                
        List<AnswerText> answerTexts=AnswerTextGetAll.executeBySurveyId(surveyId);
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);
                
        Map answerSetMap=new HashMap();
        for (AnswerSet answerSet: answerSets) {
            answerSetMap.put(answerSet.getKey().getId(), answerSet);
        }            
        
        Map answerMap=new HashMap();
        for (Answer answer: answers) {
            answerMap.put(answer.getKey().getId(), answer);
        }                
        
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        Map questionMap=new HashMap();
        for (Question question: questions) {
            questionMap.put(question.getKey().getId(), question);
        }
        
        // Link answer to answer set
        for (Answer answer: answers) {
            if (answerSetMap.containsKey(answer.getAnswerSetId())) {
                AnswerSet answerSet=(AnswerSet)answerSetMap.get(answer.getAnswerSetId());
                answerSet.getAnswers().add(answer);
            }
        }
                
        // Link answer text to language and answer
        for (AnswerText answerText: answerTexts) {
            if (languagesMap.containsKey(answerText.getLanguageId())) {
                Language language=(Language)languagesMap.get(answerText.getLanguageId());
                answerText.setLanguage(language);
            }
            if (answerMap.containsKey(answerText.getAnswerId())) {
                Answer answer=(Answer)answerMap.get(answerText.getAnswerId());
                answer.getAnswerTextMap().put(answerText.getLanguageId(),answerText);
            }
        }
        
        // Link question text to language and question
        for (QuestionText questionText: questionTexts) {
            if (languagesMap.containsKey(questionText.getLanguageId())) {
                Language language=(Language)languagesMap.get(questionText.getLanguageId());
                questionText.setLanguage((Language)languagesMap.get(questionText.getLanguageId()));
            }
            if (questionMap.containsKey(questionText.getQuestionId())) {
                Question question=(Question)questionMap.get(questionText.getQuestionId());
                question.getQuestionTextMap().put(questionText.getLanguageId(),questionText);
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