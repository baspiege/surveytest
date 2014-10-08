package surveytest.controller;

import surveytest.data.AnswerDelete;
import surveytest.data.AnswerGetSingle;
import surveytest.data.AnswerUpdate;
import surveytest.data.AnswerTextAdd;
import surveytest.data.AnswerTextDelete;
import surveytest.data.AnswerTextGetAll;
import surveytest.data.AnswerTextUpdate;
import surveytest.data.AnswerSetGetAll;
import surveytest.data.LanguageGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Language;
import surveytest.data.model.Answer;
import surveytest.data.model.AnswerText;
import surveytest.data.model.Survey;
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

/**
* Process answer updates.
*/
public class AnswerUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_UPDATE);
    }

    /**
    * Update or delete answer.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        Answer answer=(Answer)request.getAttribute(RequestUtils.ANSWER);
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");       
                
        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("updateLabel"))) {		
                // Fields
                String description=RequestUtils.getAlphaInput(request,"description",bundle.getString("descriptionLabel"),true);
                answer.setText(description);
                
                // Answer Text
                List<AnswerText> answerTexts=(List<AnswerText>)request.getAttribute(RequestUtils.ANSWER_TEXTS);
                for (AnswerText answerText: answerTexts) {
                    String answerTextLanguageId="answerText_Language_" + answerText.getLanguageId();
                    String answerTextLanguage=RequestUtils.getAlphaInput(request,answerTextLanguageId,answerText.getLanguage().getName(),true);
                    answerText.setText(answerTextLanguage);
                }
                
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {		
                deleteAction(request,response);
            }
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Answer answer=(Answer)request.getAttribute(RequestUtils.ANSWER);
        List<AnswerText> answerTexts=(List<AnswerText>)request.getAttribute(RequestUtils.ANSWER_TEXTS);
        
        if (!RequestUtils.hasEdits(request)) {
            answer=AnswerUpdate.execute(answer);
            
            for (AnswerText answerText: answerTexts) {
                if (answerText.getAnswerId()>0) {
                    AnswerTextUpdate.execute(answerText);
                } else {
                    answerText.setAnswerId(answer.getKey().getId());
                    AnswerTextAdd.execute(answerText);                
                }
            }
        }
        // If no edits, forward to answer.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("answerId",answer.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_UPDATE);
        }
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Answer answer=(Answer)request.getAttribute(RequestUtils.ANSWER);
        List<AnswerText> answerTexts=(List<AnswerText>)request.getAttribute(RequestUtils.ANSWER_TEXTS);
      
        if (!RequestUtils.hasEdits(request)) {
            AnswerDelete.execute(answer);
            
            for (AnswerText answerText: answerTexts) {
                AnswerTextDelete.execute(answerText);
            }
        }
        
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("answerSetId",answer.getAnswerSetId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        // Check if signed in
//        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
//        if (!isSignedIn) {
//            throw new SecurityException("User principal not found");
//        }

        // Get answer
        Long answerId=RequestUtils.getNumericInput(request,"answerId","answerId",true);
        Answer answer=AnswerGetSingle.execute(answerId);

        if (answer==null) {
            throw new RuntimeException("Answer not found: " + answerId);
        } else {
            request.setAttribute(RequestUtils.ANSWER,answer);
        }
        
        // Survey
        Survey survey=SurveyGetSingle.execute(answer.getSurveyId());
        request.setAttribute(RequestUtils.SURVEY, survey);

        // Get languages
        List<Language> languages=LanguageGetAll.execute(survey.getKey().getId());
        request.setAttribute(RequestUtils.LANGUAGES, languages);
                
        // Answer Texts
        List<AnswerText> answerTexts=AnswerTextGetAll.execute(answerId);
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);

        Map answerTextMap=new HashMap();
        for (AnswerText answerText: answerTexts) {
            answerTextMap.put(answerText.getLanguageId(), answerText);
        }
        
        for (Language language: languages) {
            if (!answerTextMap.containsKey(language.getKey().getId())) {
                AnswerText answerText=new AnswerText();
                answerText.setSurveyId(survey.getKey().getId());
                answerText.setAnswerSetId(answer.getAnswerSetId());
                answerText.setAnswerId(answer.getKey().getId());
                answerText.setLanguage(language);
                answerText.setLanguageId(language.getKey().getId());
                answerText.setText("");
                answerTexts.add(answerText);
            } else { 
                AnswerText answerText=(AnswerText)answerTextMap.get(language.getKey().getId());
                answerText.setLanguage(language);
            }
        }
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);
        
        // Get answers sets
        List<AnswerSet> answerSets=AnswerSetGetAll.execute(survey.getKey().getId());
        request.setAttribute(RequestUtils.ANSWER_SETS, answerSets);
    }
}