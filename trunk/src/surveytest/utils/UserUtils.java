package surveytest.utils;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    public static final String LOGGED_ON="loggedOn";
    
    public static boolean isLoggedOn(HttpServletRequest aRequest) {
        Boolean isLoggedOn=(Boolean)aRequest.getSession().getAttribute(LOGGED_ON);
        if (isLoggedOn==null || !isLoggedOn.booleanValue()) {
            return false;
        }
        if (aRequest.getUserPrincipal()==null || aRequest.getUserPrincipal().getName()==null) {
            return false;
        }
        return true;
    }
    
    public static void setLoggedOn(HttpServletRequest aRequest) {
        aRequest.getSession(true).setAttribute(UserUtils.LOGGED_ON,new Boolean(true));
    }
}