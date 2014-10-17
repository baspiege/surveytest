package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.AdminDelete;
import surveytest.data.AdminUpdate;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.Survey;
import surveytest.exceptions.UserNotFoundException;
import surveytest.utils.EditUtils;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        RequestUtils.forwardTo(request,response,ControllerConstants.ADMIN_UPDATE);
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
            if (action.equals(bundle.getString("updateLabel"))) {		
                // Fields
                String userId=RequestUtils.getAlphaInput(request,"userId",bundle.getString("userIdLabel"),true);
                admin.setUserId(userId);
                
                if (!EditUtils.hasEdits(request)) {
                    admin.setLastUpdateUserId(request.getUserPrincipal().getName());
                    admin=AdminUpdate.execute(admin);
                }
            } else if (action.equals(bundle.getString("deleteLabel"))) {		

                if (!EditUtils.hasEdits(request)) {
                    admin.setLastUpdateUserId(request.getUserPrincipal().getName());
                    AdminDelete.execute(admin);
                }         
            }
        }

        // If no edits, forward to survey.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",admin.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEY_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.ADMIN_UPDATE);
        }
    }

    private void setUpData(HttpServletRequest request) {

        if (!UserUtils.isLoggedOn(request)) {
            throw new UserNotFoundException();
        }
        
        Long adminId=RequestUtils.getNumericInput(request,"adminId","adminId",true);
        Admin admin=null;
        if (adminId!=null) {
            admin=AdminGetSingle.execute(adminId);
            request.setAttribute(RequestUtils.ADMIN, admin);
        }
        if (admin==null) {
            throw new RuntimeException("Admin not found:" + adminId);
        }
        
        String userId=request.getUserPrincipal().getName();
        Admin currentAdmin=AdminGetSingle.getByUserId(userId, admin.getSurveyId());
        if (currentAdmin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + admin.getSurveyId());
        }

    }
}