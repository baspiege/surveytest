package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionResponseGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.SurveyResponseGetAll;
import surveytest.data.model.Admin;
import surveytest.data.model.Language;
import surveytest.data.model.QuestionResponse;
import surveytest.data.model.Survey;
import surveytest.data.model.SurveyResponse;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

    public static final String DOUBLE_QUOTE="\"";
    public static final String SEPARATOR=",";
    public static final String NOT_AVAILABLE="N/A";
    public static final String END_LINE="\n";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        String report=(String)request.getAttribute(RequestUtils.SURVEY_RESPONSE_REPORT);
            
        response.setContentType("text/csv");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"SurveyResponses_" + survey.getKey().getId() + ".csv\"");

        OutputStream outputStream=null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(report.getBytes());
            outputStream.flush();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream!=null) {
                outputStream.close();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }

    private void setUpData(HttpServletRequest request) {
    
        if (!UserUtils.isLoggedOn(request)) {
            throw new UserNotFoundException();
        }
                
        Long surveyId=RequestUtils.getNumericInput(request,"surveyId","surveyId",true);
        Survey survey=null;
        if (surveyId!=null) {
            survey=SurveyGetSingle.execute(surveyId);
            request.setAttribute(RequestUtils.SURVEY, survey);
        }
        if (survey==null) {
            throw new RuntimeException("Survey not found:" + surveyId);
        }

        String userId=request.getUserPrincipal().getName();
        Admin admin=AdminGetSingle.getByUserId(userId, surveyId);
        if (admin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + surveyId);
        }
 
        List<Language> languages=LanguageGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        List<SurveyResponse> surveyResponses=SurveyResponseGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.SURVEY_RESPONSES, surveyResponses);

        List<QuestionResponse> questionResponses=QuestionResponseGetAll.execute(surveyId);
        
        Map languagesMap=new HashMap();
        for (Language language: languages) {
            languagesMap.put(language.getKey().getId(), language);
        }
        
        Map surveyResponsesMap=new HashMap();
        for (SurveyResponse surveyResponse: surveyResponses) {
            surveyResponsesMap.put(surveyResponse.getKey().getId(), surveyResponse);
        }
        
        // Add question responses to survey responses
        for (QuestionResponse questionResponse: questionResponses) {
            if (surveyResponsesMap.containsKey(questionResponse.getSurveyResponseId())) {
                SurveyResponse surveyResponse=(SurveyResponse)surveyResponsesMap.get(questionResponse.getSurveyResponseId());
                surveyResponse.getQuestionResponses().add(questionResponse);
            }
        }
        
        // Create a set of all question ids in case one of the survery
        // responses doesn't have the question.  N/A will be put in place.
        // This way, the columns will line up.
        TreeSet<Long> questionIds=new TreeSet<Long>();
        for (QuestionResponse questionResponse: questionResponses) {
            questionIds.add(questionResponse.getQuestionId());
        }
        
        StringBuilder report=new StringBuilder();

        // Header
        report.append(escapeField("Survey Response Id") + SEPARATOR);
        report.append(escapeField("Submitted Time") + SEPARATOR);
        report.append(escapeField("Identifier") + SEPARATOR);
        //report.append(escapeField("Language Id") + SEPARATOR);
        report.append(escapeField("Language") + SEPARATOR);
        for (Long questionId: questionIds) {
            //report.append(escapeField("Question Id") + SEPARATOR);
            report.append(escapeField("Question Text") + SEPARATOR);
            //report.append(escapeField("Answer Id ") + SEPARATOR);
            report.append(escapeField("Answer Text") + SEPARATOR);
        }
        report.append(END_LINE);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
                    
        for (SurveyResponse surveyResponse: surveyResponses) {
        
            Language language=(Language)languagesMap.get(surveyResponse.getLanguageId());
            
            Map questionResponsesMap=new HashMap();
            for (QuestionResponse questionResponse: surveyResponse.getQuestionResponses()) {
                questionResponsesMap.put(questionResponse.getQuestionId(), questionResponse);
            }
            
            report.append(surveyResponse.getKey().getId() + SEPARATOR);
            report.append(dateFormat.format(surveyResponse.getLastUpdateTime()) + SEPARATOR);
            report.append(escapeField(surveyResponse.getIdentifier()) + SEPARATOR);
            
            //report.append(surveyResponse.getLanguageId() + SEPARATOR);
            report.append(escapeField(language.getName()) + SEPARATOR);            
           
            for (Long questionId: questionIds) {
                if (questionResponsesMap.containsKey(questionId)) {
                    QuestionResponse questionResponse=(QuestionResponse)questionResponsesMap.get(questionId);
                    //report.append(questionResponse.getQuestionId() + SEPARATOR);
                    report.append(escapeField(questionResponse.getQuestionText()) + SEPARATOR);
                    //report.append(questionResponse.getAnswerId() + SEPARATOR);
                    report.append(escapeField(questionResponse.getAnswerText()) + SEPARATOR);
                }
                else {
                    //report.append(NOT_AVAILABLE + SEPARATOR);
                    report.append(NOT_AVAILABLE + SEPARATOR);
                    //report.append(NOT_AVAILABLE + SEPARATOR);
                    report.append(NOT_AVAILABLE + SEPARATOR);
                }
            }
            
            report.append(END_LINE);
        } 
        
        request.setAttribute(RequestUtils.SURVEY_RESPONSE_REPORT,report.toString());
    }
    
    private String escapeField(String aString) {
        return DOUBLE_QUOTE + aString.replace(DOUBLE_QUOTE, DOUBLE_QUOTE + DOUBLE_QUOTE) + DOUBLE_QUOTE;
    }
}