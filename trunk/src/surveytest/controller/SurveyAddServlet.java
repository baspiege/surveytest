package surveytest.controller;

import surveytest.data.SurveyAdd;
import surveytest.data.model.Survey;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Process survey adds.
*/
public class SurveyAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        // Default
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);
        survey.setName("");
        RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_ADD);
    }

    /**
    * Add question.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Name");
        Survey survey=(Survey)request.getAttribute(RequestUtils.SURVEY);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields
                String name=RequestUtils.getAlphaInput(request,"name",bundle.getString("nameLabel"),true);
                survey.setName(name);
                if (!RequestUtils.hasEdits(request)) {
                    survey=SurveyAdd.execute(survey);
                }
            }
        }

        // If no edits, forward to survey.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("surveyId",survey.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_ADD);
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

        // Set survey
        Survey survey=new Survey();
        //survey.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.SURVEY, survey);
    }
}