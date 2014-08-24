package surveytest.controller;

import surveytest.data.LanguageAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Language;
import surveytest.data.model.Survey;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        // Default
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        survey.setName("");
        RequestUtils.forwardTo(request,response,ControllerConstants.LANGUAGE_ADD);
    }

    /**
    * Add language.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Language language=(Language)request.getAttribute(RequestUtils.LANGUAGE);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String name=RequestUtils.getAlphaInput(request,"name",bundle.getString("nameLabel"),true);
                language.setName(name);
                if (!RequestUtils.hasEdits(request)) {
                    language=LanguageAdd.execute(language);
                }
            }
        }

        // If no edits, forward to survey.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("surveyId",language.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.LANGUAGE_ADD);
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
        request.setAttribute(RequestUtils.SURVEY, survey);
        
        // Set language
        Language language=new Language();
        language.setSurveyId(surveyId);
        //language.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.LANGUAGE, language);
    }
}