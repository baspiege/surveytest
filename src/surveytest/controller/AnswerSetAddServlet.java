package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.AnswerSetAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.AnswerSet;
import surveytest.data.model.Survey;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnswerSetAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);
        answerSet.setDescription("");
        RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_ADD);
    }

    /**
    * Add.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        AnswerSet answerSet=(AnswerSet)request.getAttribute(RequestUtils.ANSWER_SET);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String description=RequestUtils.getAlphaInput(request,"description",bundle.getString("descriptionLabel"),true);
                answerSet.setDescription(description);

                if (!EditUtils.hasEdits(request)) {
                    answerSet=AnswerSetAdd.execute(answerSet);
                }
            }
        }

        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("answerSetId", answerSet.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ANSWER_SET_ADD);
        }
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

        AnswerSet answerSet=new AnswerSet();
        answerSet.setSurveyId(survey.getKey().getId());
        answerSet.setLastUpdateUserId(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.ANSWER_SET, answerSet);
    }
}