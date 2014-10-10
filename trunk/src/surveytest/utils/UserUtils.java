package surveytest.utils;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    public static final String LOGGED_ON="loggedOn";
    
    public static boolean isLoggedOn(HttpServletRequest aRequest) {
        Boolean isLoggedOn=(Boolean)aRequest.getSession().getAttribute(LOGGED_ON);
        if (isLoggedOn==null || !isLoggedOn.booleanValue()) {
            return false;
        }
        return true;
    }
}    