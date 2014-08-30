package surveytest.controller;

import surveytest.data.LanguageGetAll;
import surveytest.data.QuestionAdd;
import surveytest.data.QuestionTextAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Language;
import surveytest.data.model.Question;
import surveytest.data.model.QuestionText;
import surveytest.data.model.Survey;
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

/**
* Process question adds.
*/
public class QuestionAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        // Default
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        question.setText("");
        RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_ADD);
    }

    /**
    * Add question.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        List<Language> languages=(List<Language>)request.getAttribute(RequestUtils.LANGUAGES);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String note=RequestUtils.getAlphaInput(request,"note",bundle.getString("noteLabel"),true);
                question.setText(note);

                // Question Text
                List<QuestionText> questionTexts=new ArrayList<QuestionText>();
                for (Language language: languages) {
                    String questionTextLanguageId="questionText_Language_" + language.getKey().getId();
                    String questionTextLanguage=RequestUtils.getAlphaInput(request,questionTextLanguageId,bundle.getString("languageLabel"),true);
                    QuestionText questionText=new QuestionText();
                    questionText.setSurveyId(survey.getKey().getId());
                    questionText.setLanguageId(language.getKey().getId());
                    questionText.setText(questionTextLanguage);
                    questionTexts.add(questionText);
                }

                if (!RequestUtils.hasEdits(request)) {
                    question=QuestionAdd.execute(question);

                    for (QuestionText questionText: questionTexts) {
                        // Question Id is only available after Question has been saved above
                        questionText.setQuestionId(question.getKey().getId());
                        QuestionTextAdd.execute(questionText);
                    }
                }
            }
        }

        // If no edits, forward to question.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("questionId",question.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_ADD);
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

        // Set question
        Question question=new Question();
        question.setSurveyId(survey.getKey().getId());
        //question.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.QUESTION, question);

        // Get languages
        List<Language> languages=LanguageGetAll.execute(surveyId, 0L, null);
        request.setAttribute(RequestUtils.LANGUAGES, languages);

        // Question Texts
        List<QuestionText> questionTexts=new ArrayList<QuestionText>();

        for (Language language: languages) {
            QuestionText questionText=new QuestionText();
            questionText.setLanguage(language);
            questionText.setText("");
            questionTexts.add(questionText);
        }
        request.setAttribute(RequestUtils.QUESTION_TEXTS, questionTexts);
    }
}