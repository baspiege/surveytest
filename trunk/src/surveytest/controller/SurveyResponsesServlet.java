package surveytest.controller;

import surveytest.data.model.Language;
import surveytest.data.model.QuestionResponse;
import surveytest.data.model.Survey;
import surveytest.data.model.SurveyResponse;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionResponseGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.SurveyResponseGetAll;
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

public class SurveyResponsesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_RESPONSES);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {
    
        SurveyResponse surveyResponse=new SurveyResponse();
        request.setAttribute(RequestUtils.SURVEY_RESPONSE, surveyResponse);
            
        // Check survey
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
 
        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        List<SurveyResponse> surveyResponses=SurveyResponseGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.SURVEY_RESPONSES, surveyResponses);
        
        // Language map
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        // Get question responses
        List<QuestionResponse> questionResponses=QuestionResponseGetAll.execute(surveyId);
        surveyResponse.setQuestionResponses(questionResponses);
        
        // Create list of all question ids
        // If survey doesn't have one, put N/A
        
        String SEPARATOR="\",\";
        
        StringBuilder report=new StringBuilder();
        for (QuestionResponse questionResponse: questionResponses) {

            // TODO - Add survey response identifier, language text, question id, answer id
        
            // Language
            report.append(questionResponse.getLanguageId() + SEPARATOR);
            
            report.append(escapeField(questionResponse.getQuestionText()) + SEPARATOR);
            report.append(escapeField(questionResponse.getAnswerText()) + "\"\n");
        } 
        
        request.setAttribute(RequestUtils.SURVEY_RESPONSE_REPORT,report.toString());
    }
    
    private String escapeField(String aString) {
        return aString.replace("\"","\"\");
    }
}