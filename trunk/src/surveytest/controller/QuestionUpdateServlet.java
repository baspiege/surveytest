package surveytest.controller;

import surveytest.data.QuestionDelete;
import surveytest.data.QuestionUpdate;
import surveytest.data.model.Question;
import surveytest.utils.RequestUtils;
import surveytest.utils.StringUtils;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Process question updates.
*/
public class QuestionUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
    }

    /**
    * Update or delete question.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("updateLabel"))) {		
                String note=RequestUtils.getAlphaInput(request,"note",bundle.getString("nameLabel"),true);
                question.setText(note);
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {		
                deleteAction(request,response);
            }
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.STORES_REDIRECT);
        }
    }

    /**
    * Update action.
    */
    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
        if (!RequestUtils.hasEdits(request)) {
            question=QuestionUpdate.execute(question);
        }
        // If no edits, forward to question.
        if (!RequestUtils.hasEdits(request)) {
            request.setAttribute("questionId",question.getKey().getId());
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
        }
    }

    /**
    * Delete action.
    */
    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Question question=(Question)request.getAttribute(RequestUtils.QUESTION);
      
        if (!RequestUtils.hasEdits(request)) {
            QuestionDelete.execute(question);
        }
        // If no edits, forward to ?
        if (!RequestUtils.hasEdits(request)) {

        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.QUESTION_UPDATE);
        }
    }

    /**
    * Set-up the data.
    */
    private void setUpData(HttpServletRequest request) {

        // Check if signed in
        /*
        boolean isSignedIn=request.getUserPrincipal().getName()!=null;
        if (!isSignedIn) {
            throw new SecurityException("User principal not found");
        }
        */

        // Get question
        Long questionId=RequestUtils.getNumericInput(request,"questionId","questionId",true);
        Question question=null;

        if (question==null) {
            throw new RuntimeException("Question not found: " + questionId);
        }

        //question.setUser(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.QUESTION, question);
    }
}