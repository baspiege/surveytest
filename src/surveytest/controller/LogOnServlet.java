package surveytest.controller;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import surveytest.utils.RequestUtils;
import surveytest.utils.UserUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOnServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        
        if (!UserUtils.isLoggedOn(request)) {
            RequestUtils.forwardTo(request,response,ControllerConstants.LOG_ON);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        if (!UserUtils.isLoggedOn(request)) {
            RequestUtils.forwardTo(request,response,ControllerConstants.LOG_ON);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.SURVEYS_REDIRECT);
        }
    }

    private void setUpData(HttpServletRequest request) {

        boolean isSignedIn=request.getUserPrincipal()!= null;
        if (isSignedIn) {
            UserUtils.setLoggedOn(request);
        }
        
        UserService userService = UserServiceFactory.getUserService();
        String postLogonUrl = RequestUtils.getUri(request, ControllerConstants.SURVEYS_REDIRECT);
        String logOnUrl=userService.createLoginURL(postLogonUrl);
        request.setAttribute("logOnUrl",logOnUrl);
    }
}