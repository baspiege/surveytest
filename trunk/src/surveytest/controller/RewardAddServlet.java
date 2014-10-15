package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.RewardAdd;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Reward;
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

public class RewardAddServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        RequestUtils.forwardTo(request,response,ControllerConstants.REWARD_ADD);
    }

    /**
    * Add reward.
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);
        String action=RequestUtils.getAlphaInput(request,"action","Action",true);
        ResourceBundle bundle = ResourceBundle.getBundle("Text");
        Reward reward=(Reward)request.getAttribute(RequestUtils.REWARD);

        // Process based on action
        if (!StringUtils.isEmpty(action)) {
            if (action.equals(bundle.getString("addLabel"))) {		
                // Fields                
                String url=RequestUtils.getAlphaInput(request,"url",bundle.getString("urlLabel"),true);
                reward.setUrl(url);
                
                if (!EditUtils.hasEdits(request)) {
                    reward=RewardAdd.execute(reward);
                }
            }
        }

        // If no edits, forward to rewards.
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",reward.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARDS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARD_ADD);
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
        Admin admin=AdminGetSingle.getByUserId(userId, surveyId);
        if (admin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + surveyId);
        }

        
        Reward reward=new Reward();
        reward.setSurveyId(surveyId);
        reward.setLastUpdateUserId(request.getUserPrincipal().getName());
        request.setAttribute(RequestUtils.REWARD, reward);
    }
}