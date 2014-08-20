package surveytest.controller;

import surveytest.data.model.Survey;
import surveytest.utils.RequestUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Show surveys.
*/
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
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS);
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

        List surveys=new ArrayList();
        
        // TODO - Get data from database.  Use test data temporarily.
        Survey survey=new Survey();
        survey.setName("test1");
        
        surveys.add(survey);
        surveys.add(survey);
        
        request.setAttribute(RequestUtils.SURVEYS,surveys);
    }
}