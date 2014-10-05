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
                String introText=RequestUtils.getAlphaInput(request,"introText",bundle.getString("introTextLabel"),true);
                String confirmationText=RequestUtils.getAlphaInput(request,"confirmationText",bundle.getString("confirmationTextLabel"),true);
                String submitButtonText=RequestUtils.getAlphaInput(request,"submitButtonText",bundle.getString("submitButtonTextLabel"),true);
                String surveyName=RequestUtils.getAlphaInput(request,"surveyName",bundle.getString("surveyNameLabel"),true);
                String identifierText=RequestUtils.getAlphaInput(request,"identifierText",bundle.getString("identifierTextLabel"),true);
                language.setName(name);
                language.setIntroText(introText);
                language.setConfirmationText(confirmationText);
                language.setSubmitButtonText(submitButtonText);
                language.setSurveyName(surveyName);
                language.setIdentifierText(identifierText);
                
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
        
        // Set language
        Language language=new Language();
        language.setSurveyId(surveyId);
        //language.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.LANGUAGE, language);
    }
}