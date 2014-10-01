package surveytest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Request utilities.
 *
 * @author Brian Spiegel
 */
public class RequestUtils {

    public static String EDITS="edits";
   
    public static String ANSWER="answer";
    public static String ANSWER_SET="answerSet";
    public static String ANSWER_SETS="answerSets";
    public static String ANSWER_TEXTS="answerTexts";
    public static String ANSWERS="answers";
    
    public static String LANGUAGE="language";
    public static String LANGUAGES="languages";
    
    public static String QUESTION="question";
    public static String QUESTION_TEXTS="questionTexts";
    public static String QUESTIONS="questions";
    
    public static String SURVEY="survey";
    public static String SURVEYS="surveys";

    public static String SURVEY_RESPONSE="surveyResponse";
    public static String SURVEY_RESPONSES="surveyResponses";
    
    // These are thread-safe.
    private static Pattern mNumbersPattern=Pattern.compile("[-]?[\\d]*[\\.]?[\\d]*");

    /**
    * Add edit.
    *
    * @param aRequest Servlet Request
    * @param aEditMessage edit message
    */
    public static void addEdit(HttpServletRequest aRequest, String aEditMessage) {
        getEdits(aRequest).add(aEditMessage);
    }

    /**
    * Add edit.
    *
    * @param aRequest Servlet Request
    * @param aKey key in Text ResourceBundle
    */
    public static void addEditUsingKey(HttpServletRequest aRequest, String aKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        getEdits(aRequest).add(bundle.getString(aKey));
    }

    /**
    * Get a String input and store into the request if there are no edits.
    *
    * @param aRequest Servlet Request to get input from
    * @param aFieldToCheck Field to check
    * @param aDescription Description of field for edit message
    * @param aRequired Indicates if required
    * @return the field if no edits
    */
    public static String getAlphaInput(HttpServletRequest aRequest, String aFieldToCheck, String aDescription, boolean aRequired) {
        String value=aRequest.getParameter(aFieldToCheck);
        if (isFieldEmpty(aRequest, value, aFieldToCheck, aDescription, aRequired)) {
            value="";
        } else if (value.length()>500) {
            value=value.substring(0,500);
            ResourceBundle bundle = ResourceBundle.getBundle("Text");
            String editMessage=aDescription + ": " + bundle.getString("alphaFieldMaxLengthEdit");
            addEdit(aRequest,editMessage);
        } else {
            value=value.trim();
        }

        return value;
    }

    /**
    * Get the edits.
    *
    * @return a list of edits
    */
    public static List<String> getEdits(HttpServletRequest aRequest) {
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits==null) {
            edits=new ArrayList<String>();
            aRequest.setAttribute(EDITS,edits);
        }

        return edits;
    }

    /**
    * Get a numeric input and store into the request if there are no edits.
    *
    * @param aRequest Servlet Request to get input from
    * @param aFieldToCheck Field to check
    * @param aDescription Description of field for edit message
    * @param aRequired Indicates if required
    * @return the field if no edits
    */
    public static Long getNumericInput(HttpServletRequest aRequest, String aFieldToCheck, String aDescription, boolean aRequired) {
        Long retValue=null;
        String value=aRequest.getParameter(aFieldToCheck);
        if (isFieldEmpty(aRequest, value, aFieldToCheck, aDescription, aRequired)) {
            // Do nothing
        } else if (!mNumbersPattern.matcher(value).matches()) {
            retValue=null;

            ResourceBundle bundle = ResourceBundle.getBundle("Text");
            String editMessage=aDescription + ": " + bundle.getString("numberFieldValidCharsEdit");
            addEdit(aRequest,editMessage);
        } else {
            try {
                retValue=new Long(value);
            } catch (NumberFormatException e) {
                retValue=null;
                ResourceBundle bundle = ResourceBundle.getBundle("Text");
                String editMessage=aDescription + ": " + bundle.getString("numberFieldNotValidEdit");
                addEdit(aRequest,editMessage);
            }
        }

        return retValue;
    }

   /**
    * Forward to.
    *
    * @param aRequest Servlet Request
    * @param aResponse Servlet Response
    */
    public static void forwardTo(HttpServletRequest aRequest, HttpServletResponse aResponse, String target) throws IOException, ServletException {
        RequestDispatcher rd=aRequest.getRequestDispatcher(target);
        rd.forward(aRequest, aResponse);
    }

    /**
    * Has edits.
    *
    * @param aRequest Servlet Request
    * @return a boolean indicating if there are edits
    */
    public static boolean hasEdits(HttpServletRequest aRequest) {
        boolean hasEdits=false;
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits!=null && edits.size()>0) {
            hasEdits=true;
        }

        return hasEdits;
    }

    /**
    * Check if empty.  If required, create an edit.
    *
    * @param aRequest Servlet Request to get input from
    * @param aValue value to check
    * @param aDescription Description of field for edit message
    * @param aRequired Indicates if required
    * @return a boolean indicating if the field is empty
    */
    private static boolean isFieldEmpty(HttpServletRequest aRequest, String aValue, String aFieldToCheck, String aDescription, boolean aRequired) {
        boolean isEmpty=false;

        if (aValue==null || aValue.trim().length()==0) {
            isEmpty=true;
            if (aRequired) {
                ResourceBundle bundle = ResourceBundle.getBundle("Text");
                String editMessage=aDescription + ": " + bundle.getString("fieldRequiredEdit");
                addEdit(aRequest,editMessage);
            }
        }

        return isEmpty;
    }

    /**
    * Remove edits.
    *
    * @param aRequest Servlet Request
    */
    public static void removeEdits(HttpServletRequest aRequest) {
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits!=null && edits.size()>0) {
            edits.clear();
        }
    }
    
    /**
    * Set no cache headers.
    *
    * @param aResponse Servlet Response
    */
    public static void setNoCacheHeaders(HttpServletResponse aResponse) {
        aResponse.setHeader("Cache-Control","no-cache");
        aResponse.setHeader("Pragma","no-cache");
        aResponse.setDateHeader ("Expires", -1);
    }

    /**
    * Set cache headers.
    *
    * @param aResponse Servlet Response
    */
    public static void setCacheHeaders(HttpServletResponse aResponse, int days) {
        long cacheInSeconds = 60 * 60 * 24 * days;        
        long now = System.currentTimeMillis();
        aResponse.addHeader("Cache-Control", "max-age=" + cacheInSeconds);
        aResponse.setDateHeader("Last-Modified", now);
        aResponse.setDateHeader("Expires", now + cacheInSeconds * 1000);
    }    
}
