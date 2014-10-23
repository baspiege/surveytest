package surveytest.controller;

import surveytest.data.model.Language;
import surveytest.data.model.Reward;
import surveytest.data.model.Survey;
import surveytest.data.LanguageGetAll;
import surveytest.data.RewardGetSingle;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ResourceBundle;
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
                Long languageId=RequestUtils.getNumericInput(request,"languageId",bundle.getString("languageLabel"),true);
            
                if (!EditUtils.hasEdits(request)) {
            
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
                        EditUtils.addEdit(request,bundle.getString("languageNotFoundMessage"));
                    }
                }
            }
        }
        
        // If no edits, forward to survey response with survey and language id.
        if (!EditUtils.hasEdits(request) && language!=null) {
            request.setAttribute("surveyId",language.getSurveyId());
            request.setAttribute("languageId",language.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSE_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_LANGUAGE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        Long rewardId=RequestUtils.getNumericInput(request,"rewardId","rewardId",false);
        Reward reward=null;
        if (rewardId!=null) {
            reward=RewardGetSingle.execute(rewardId);
            request.setAttribute(RequestUtils.REWARD, reward);
        }
        
        // Check token
        if (reward!=null) {
            Long token=RequestUtils.getNumericInput(request,"token","token",true);
            if (token==null) {
                throw new RuntimeException("Token not found");
            }            
            if (reward.getToken()!=token) {
                String message="Inputted token not valid for reward id: " + reward.getKey().getId()
                    + " reward token: " + reward.getToken()
                    + " inputted token: " + tokenId; 
                throw new RuntimeException(message);
            }
            if (reward.getUsed()) {
                throw new RuntimeException("Reward already used");
            }
        }
        
        // If no reward, check for survey Id.
        Long surveyId=null;
        if (reward==null) {
            surveyId=RequestUtils.getNumericInput(request,"surveyId","surveyId",false);
        } else {
            surveyId=reward.getSurveyId();
        }
        
        Survey survey=null;
        if (surveyId!=null) {
            survey=SurveyGetSingle.execute(surveyId);
            request.setAttribute(RequestUtils.SURVEY, survey);
        }
        if (survey==null) {
            throw new RuntimeException("Survey not found:" + surveyId);
        }
        
        List<Language> languages=LanguageGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.LANGUAGES, languages);
    }
}