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
        RequestUtils.forwardTo(request,response,ControllerConstants.LOG_ON);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.LOG_ON);
    }

    private void setUpData(HttpServletRequest request) {
    
        // Check if signed in by checking Google user info.
        // If signed in, mark session as logged on.
        UserService userService = UserServiceFactory.getUserService();
        boolean isSignedIn=request.getUserPrincipal()!= null;
        if (isSignedIn) {
            request.getSession(true).setAttribute(UserUtils.LOGGED_ON,new Boolean(true));
        }
        
        String postLogonUrl = RequestUtils.getUri(request, "/surveys");
        String logOnUrl=userService.createLoginURL(postLogonUrl);
        request.setAttribute("logOnUrl",logOnUrl);
    }
}