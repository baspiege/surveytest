package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.AnswerSetGetSingle;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionGetSingle;
import surveytest.data.QuestionTextGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
import surveytest.data.model.Survey;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.RequestUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
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
        
        String userId=request.getUserPrincipal().getName();
        Admin admin=AdminGetSingle.getByUserId(userId, question.getSurveyId());
        if (admin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + question.getSurveyId());
        }
        
        List<QuestionText> questionTexts=QuestionTextGetAll.execute(questionId);
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
        
        Survey survey=SurveyGetSingle.execute(question.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);
        
        AnswerSet answerSet=null;
        if (question.getAnswerSetId()!=0) {        
            answerSet=AnswerSetGetSingle.execute(question.getAnswerSetId());
        }
        if (answerSet==null) {
            answerSet=new AnswerSet();
            answerSet.setDescription("");
        }
        request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
        
        List<Language> languages=LanguageGetAll.execute(question.getSurveyId());
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        for (QuestionText questionText: questionTexts) {
            if (languagesMap.containsKey(questionText.getLanguageId())) {
                Language language=(Language)languagesMap.get(questionText.getLanguageId());
                questionText.setLanguage(language);
            }
        }
    }
}