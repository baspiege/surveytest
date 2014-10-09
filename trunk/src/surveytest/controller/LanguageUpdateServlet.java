package surveytest.controller;

import surveytest.data.LanguageGetSingle;
import surveytest.data.LanguageDelete;
import surveytest.data.LanguageUpdate;
import surveytest.data.QuestionTextGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Language;
import surveytest.data.model.QuestionText;
import surveytest.data.model.Survey;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        RequestUtils.forwardTo(request,response,ControllerConstants.LANGUAGE_UPDATE);
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
            if (action.equals(bundle.getString("updateLabel"))) {		
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
                if (!EditUtils.hasEdits(request)) {
                    language=LanguageUpdate.execute(language);
                }
            } else if (action.equals(bundle.getString("deleteLabel"))) {		
                List<QuestionText> questionTexts=QuestionTextGetAll.executeByLanguageId(language.getKey().getId());
                
                // TODO - How can language be deleted?  Remove all questions first?  Or delete question text as well.
                // Or set language to inactive?
                if (!questionTexts.isEmpty()) {
                    EditUtils.addEditUsingKey(request,"languageCantBeDeletedWithQuestionsMessage");
                }
                if (!EditUtils.hasEdits(request)) {
                    LanguageDelete.execute(language);
                }         
            }
        }

        // If no edits, forward to survey.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",language.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.LANGUAGE_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        // Check if signed in
//        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
//        if (!isSignedIn) {
//            throw new SecurityException("User principal not found");
//        }
        
        // Language
        Long languageId=RequestUtils.getNumericInput(request,"languageId","languageId",true);
        Language language=null;
        if (languageId!=null) {
            language=LanguageGetSingle.execute(languageId);
            request.setAttribute(RequestUtils.LANGUAGE, language);
        }
        if (language==null) {
            throw new RuntimeException("Language not found:" + languageId);
        }
    }
}