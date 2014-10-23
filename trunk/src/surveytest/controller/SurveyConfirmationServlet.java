package surveytest.controller;

import surveytest.data.model.Language;
import surveytest.data.model.Reward;
import surveytest.data.model.Survey;
import surveytest.data.model.SurveyResponse;
import surveytest.data.LanguageGetAll;
import surveytest.data.LanguageGetSingle;
import surveytest.data.RewardGetSingle;
import surveytest.data.SurveyGetSingle;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveyConfirmationServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_CONFIRMATION);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
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
    }
}