package surveytest.controller;

import surveytest.data.model.Language;
import surveytest.data.model.Survey;
import surveytest.data.LanguageGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveyLanguageServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_LANGUAGE);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        Language language=null;

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("submitLabel"))) {		

                // Fields
                Long languageId=RequestUtils.getNumericInput(request,"language",bundle.getString("languageLabel"),true);
            
                if (!RequestUtils.hasEdits(request)) {
            
                    // Check if valid language
                    List<Language> languages=(List<Language>)request.getAttribute(RequestUtils.LANGUAGES);
                    boolean found=false;
                    for (Language languageValid: languages) {
                        if (languageValid.getKey().getId()==languageId) {
                            found=true;
                            language=languageValid;
                        }
                    }
                    if (!found) {
                        RequestUtils.addEdit(request,bundle.getString("languageNotFoundMessage"));
                    }
                }
            }
        }
        
        // If no edits, forward to survey response with survey and language id.
        if (!RequestUtils.hasEdits(request) && language!=null) {
            request.setAttribute("surveyId",language.getSurveyId());
            request.setAttribute("languageId",language.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSE_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_LANGUAGE);
        }
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
        
        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
                
        // Language map
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
    }
}