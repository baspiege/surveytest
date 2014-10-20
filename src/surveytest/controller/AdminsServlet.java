package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.Survey;
import surveytest.data.AdminGetAll;
import surveytest.data.SurveyGetSingle;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.RequestUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminsServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.ADMINS);
    }

    /**
    * No post for now.  Route to main page.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
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
        
        List<Admin> admins=AdminGetAll.execute(surveyId);
        request.setAttribute(RequestUtils.ADMINS, admins);
    }
}