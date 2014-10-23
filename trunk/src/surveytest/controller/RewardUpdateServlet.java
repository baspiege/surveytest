package surveytest.controller;

import surveytest.data.AdminGetSingle;
import surveytest.data.RewardGetSingle;
import surveytest.data.RewardDelete;
import surveytest.data.RewardUpdate;
import surveytest.data.SurveyGetSingle;
import surveytest.data.model.Admin;
import surveytest.data.model.Reward;
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

public class RewardUpdateServlet extends HttpServlet {

    /**
    * Display page.
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUpData(request);

        RequestUtils.forwardTo(request,response,ControllerConstants.REWARD_UPDATE);
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
            if (action.equals(bundle.getString("updateLabel"))) {		
                // Fields
                String url=RequestUtils.getAlphaInput(request,"url",bundle.getString("urlLabel"),true);
                reward.setUrl(url);
                
                updateAction(request,response);
            } else if (action.equals(bundle.getString("deleteLabel"))) {
                deleteAction(request,response);
            }
        }
    }
    
    private void updateAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Reward reward=(Reward)request.getAttribute(RequestUtils.REWARD);
        
        if (!EditUtils.hasEdits(request)) {
            reward.setLastUpdateUserId(request.getUserPrincipal().getName());
            reward=RewardUpdate.execute(reward);
        }
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",reward.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARDS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARD_UPDATE);
        }
    }

    private void deleteAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reward reward=(Reward)request.getAttribute(RequestUtils.REWARD);
      
        if (!EditUtils.hasEdits(request)) {
            reward.setLastUpdateUserId(request.getUserPrincipal().getName());
            RewardDelete.execute(reward);
        }
        
        if (!EditUtils.hasEdits(request)) {
            request.setAttribute("surveyId",reward.getSurveyId());
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARDS_REDIRECT);
        } else {
            RequestUtils.forwardTo(request,response,ControllerConstants.REWARD_UPDATE);
        }
    }


    private void setUpData(HttpServletRequest request) {

        if (!UserUtils.isLoggedOn(request)) {
            throw new UserNotFoundException();
        }
        
        Long rewardId=RequestUtils.getNumericInput(request,"rewardId","rewardId",true);
        Reward reward=null;
        if (rewardId!=null) {
            reward=RewardGetSingle.execute(rewardId);
            request.setAttribute(RequestUtils.REWARD, reward);
        }
        if (reward==null) {
            throw new RuntimeException("Reward not found:" + rewardId);
        }
        
        String userId=request.getUserPrincipal().getName();
        Admin admin=AdminGetSingle.getByUserId(userId, reward.getSurveyId());
        if (admin==null) {
            throw new RuntimeException("Admin not authorized for survey.  userId: " + userId + " surveyId: " + reward.getSurveyId());
        }

    }
}