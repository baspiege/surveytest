package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.Answer;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.AnswerText;
import surveytest.data.model.Language;
import surveytest.data.model.Survey;
import surveytest.data.LanguageGetAll;
import surveytest.data.AnswerGetSingle;
import surveytest.data.AnswerSetGetSingle;
import surveytest.data.AnswerTextGetAll;
import surveytest.data.SurveyGetSingle;
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
* Show answer.
*/
public class AnswerServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER);
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

        Long answerId=RequestUtils.getNumericInput(request,"answerId","answerId",true);
        Answer answer=AnswerGetSingle.execute(answerId);

        if (answer==null) {
            throw new RuntimeException("Answer not found: " + answerId);
        } else {
            request.setAttribute(RequestUtils.ANSWER,answer);
        }
        
        AnswerSet answerSet=AnswerSetGetSingle.execute(answer.getAnswerSetId());
        request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
        
        String userId=request.getUserPrincipal().getName();
        Admin admin=AdminGetSingle.getByUserId(userId, answerSet.getSurveyId());
        if (admin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + answerSet.getSurveyId());
        }
        
        List<AnswerText> answerTexts=AnswerTextGetAll.execute(answerId);
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);
        
        Survey survey=SurveyGetSingle.execute(answer.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);
        
        List<Language> languages=LanguageGetAll.execute(answer.getSurveyId());
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        for (AnswerText answerText: answerTexts) {
            if (languagesMap.containsKey(answerText.getLanguageId())) {
                Language language=(Language)languagesMap.get(answerText.getLanguageId());
                answerText.setLanguage(language);
            }
        }
    }
}