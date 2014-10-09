package surveytest.controller;

import surveytest.data.model.Language;
import surveytest.data.model.Survey;
import surveytest.data.LanguageGetSingle;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.RequestUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.LANGUAGE);
    }

    /**
    * No post for now.  Route to main page.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
    }

    private void setUpData(HttpServletRequest request) {
        
        Long languageId=RequestUtils.getNumericInput(request,"languageId","languageId",true);
        Language language=null;
        if (languageId!=null) {
            language=LanguageGetSingle.execute(languageId);
            request.setAttribute(RequestUtils.LANGUAGE, language);
        }
        if (language==null) {
            throw new RuntimeException("Language not found:" + languageId);
        }
        
        Survey survey=SurveyGetSingle.execute(language.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);
       
        if (survey==null) {
            throw new RuntimeException("Survey not found:" + language.getSurveyId());
        }
    }
}