package surveytest.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class EditUtils {

    public static String EDITS="edits";
   
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

    public static List<String> getEdits(HttpServletRequest aRequest) {
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits==null) {
            edits=new ArrayList<String>();
            aRequest.setAttribute(EDITS,edits);
        }
        return edits;
    }

    public static boolean hasEdits(HttpServletRequest aRequest) {
        boolean hasEdits=false;
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits!=null && edits.size()>0) {
            hasEdits=true;
        }
        return hasEdits;
    }

    public static void removeEdits(HttpServletRequest aRequest) {
        List<String> edits=(List<String>)aRequest.getAttribute(EDITS);
        if (edits!=null && edits.size()>0) {
            edits.clear();
        }
    }
}
