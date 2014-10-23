package surveytest.controller;

import surveytest.data.SurveyGetAll;
import surveytest.data.model.Survey;
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

public class SurveysServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS);
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
    
        List<Survey> surveys=new SurveyGetAll().execute();        
        request.setAttribute(RequestUtils.SURVEYS,surveys);
    }
}