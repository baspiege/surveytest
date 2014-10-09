package surveytest.controller;

import surveytest.data.LanguageGetAll;
import surveytest.data.AnswerAdd;
import surveytest.data.AnswerSetGetSingle;
import surveytest.data.AnswerTextAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Language;
import surveytest.data.model.Answer;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.AnswerText;
import surveytest.data.model.Survey;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        // Default
        Answer answer=(Answer)request.getAttribute(RequestUtils.ANSWER);
        answer.setText("");
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_ADD);
    }

    /**
    * Add answer.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        
        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);
        Answer answer=(Answer)request.getAttribute(RequestUtils.ANSWER);
        List<Language> languages=(List<Language>)request.getAttribute(RequestUtils.LANGUAGES);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String description=RequestUtils.getAlphaInput(request,"description",bundle.getString("descriptionLabel"),true);
                answer.setText(description);

                // Text
                List<AnswerText> answerTexts=(List<AnswerText>)request.getAttribute(RequestUtils.ANSWER_TEXTS);                
                for (AnswerText answerText: answerTexts) {
                    String answerTextLanguageId="answerText_Language_" + answerText.getLanguageId();
                    String answerTextLanguage=RequestUtils.getAlphaInput(request,answerTextLanguageId, answerText.getLanguage().getName(),true);
                    answerText.setText(answerTextLanguage);
                }

                if (!EditUtils.hasEdits(request)) {
                    answer=AnswerAdd.execute(answer);

                    for (AnswerText answerText: answerTexts) {
                        // Answer Id is only available after Answer has been saved above
                        answerText.setAnswerId(answer.getKey().getId());
                        AnswerTextAdd.execute(answerText);
                    }
                }
            }
        }

        // If no edits, forward to answer.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("answerId",answer.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_ADD);
        }
    }

    private void setUpData(HttpServletRequest request) {

        // Check if signed in
//        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
//        if (!isSignedIn) {
//            throw new SecurityException("User principal not found");
//        }

        // Check answer set
        Long answerSetId=RequestUtils.getNumericInput(request,"answerSetId","answerSetId",true);
        AnswerSet answerSet=null;
        if (answerSetId!=null) {
            answerSet=AnswerSetGetSingle.execute(answerSetId);
            request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
        }
        if (answerSet==null) {
            throw new RuntimeException("Answer Set not found:" + answerSetId);
        }

        Answer answer=new Answer();
        answer.setSurveyId(answerSet.getSurveyId());
        answer.setAnswerSetId(answerSet.getKey().getId());
        request.setAttribute(RequestUtils.ANSWER, answer);

        List<Language> languages=LanguageGetAll.execute(answer.getSurveyId());
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        List<AnswerText> answerTexts=new ArrayList<AnswerText>();
        for (Language language: languages) {
            AnswerText answerText=new AnswerText();
            answerText.setSurveyId(answer.getSurveyId());
            answerText.setLanguageId(language.getKey().getId());
            answerText.setLanguage(language);
            answerText.setText("");
            answerTexts.add(answerText);
        }
        request.setAttribute(RequestUtils.ANSWER_TEXTS, answerTexts);
    }
}