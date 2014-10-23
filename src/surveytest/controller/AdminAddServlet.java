package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.AdminAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.Survey;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        RequestUtils.forwardTo(request,response,ControllerConstants.ADMIN_ADD);
    }

    /**
    * Add admin.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Admin admin=(Admin)request.getAttribute(RequestUtils.ADMIN);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields                
                String userId=RequestUtils.getAlphaInput(request,"userId",bundle.getString("userIdLabel"),true);
                admin.setUserId(userId);
                
                if (!EditUtils.hasEdits(request)) {
                    admin=AdminAdd.execute(admin);
                }
            }
        }

        // If no edits, forward to admins.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",admin.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.ADMINS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ADMIN_ADD);
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
        Admin currentAdmin=AdminGetSingle.getByUserId(userId, surveyId);
        if (currentAdmin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + surveyId);
        }
        
        Admin admin=new Admin();
        admin.setSurveyId(surveyId);
        admin.setLastUpdateUserId(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.ADMIN, admin);
    }
}