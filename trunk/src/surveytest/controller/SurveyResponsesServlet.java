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
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveyResponsesServlet extends HttpServlet {

    public static final String SEPARATOR="\",\"";
    public static final String NOT_AVAILABLE="N/A";
    public static final String END_LINE="\"\n";

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

        // Get question responses
        List<QuestionResponse> questionResponses=QuestionResponseGetAll.execute(surveyId);
        
        // Language map
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        // Survey responses map
        Map surveyResponsesMap=new HashMap();
        for (SurveyResponse surveyResponse: surveyResponses) {
            surveyResponsesMap.put(surveyResponse.getKey().getId(), surveyResponse);
        }
        
        // Create a set of all question ids in case one of the survery
        // responses doesn't have the question.  N/A will be put in place.
        // This way, the columns will line up.
        TreeSet<Long> questionIds=new TreeSet<Long>();
        for (QuestionResponse questionResponse: questionResponses) {
            questionIds.add(questionResponse.getQuestionId());
        }
        
        StringBuilder report=new StringBuilder();
        for (QuestionResponse questionResponse: questionResponses) {
        
            SurveyResponse surveyResponse=(SurveyResponse)surveyResponsesMap.get(questionResponse.getSurveyId());
            Language language=(Language)languagesMap.get(surveyResponse.getLanguageId());
            
            // Survey Id
            report.append(surveyResponse.getKey().getId() + SEPARATOR);
            
            // Language
            report.append(surveyResponse.getLanguageId() + SEPARATOR);
            report.append(language.getName() + SEPARATOR);            
           
            for (Long questionId: questionIds) {
            
                if (questionIds.contains(questionResponse.getQuestionId())) {
                    report.append(questionResponse.getQuestionId() + SEPARATOR);
                    report.append(escapeField(questionResponse.getQuestionText()) + SEPARATOR);
                    report.append(questionResponse.getAnswerId() + SEPARATOR);
                    report.append(escapeField(questionResponse.getAnswerText()) + END_LINE);
                }
                else {
                    report.append(NOT_AVAILABLE + SEPARATOR);
                    report.append(NOT_AVAILABLE + SEPARATOR);
                    report.append(NOT_AVAILABLE + SEPARATOR);
                    report.append(NOT_AVAILABLE + END_LINE);
                }
            }
        } 
        
        request.setAttribute(RequestUtils.SURVEY_RESPONSE_REPORT,report.toString());
    }
    
    private String escapeField(String aString) {
        return aString.replace("\"","\"\"");
    }
}