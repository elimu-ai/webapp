package ai.elimu.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sign-on")
public class SignOnController {

    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.debug("handleRequest");
    	
        return "sign-on";
    }

    /**
     * To make it possible to sign on without an active Internet connection, 
     * enable sign-on with a test user during offline development.
     */
    @RequestMapping("/offline")
    public String handleOfflineSignOnRequest(HttpServletRequest request) {
    	logger.info("handleOfflineSignOnRequest");
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            // Fetch the test user that was created in DbContentImportHelper during application launch
            Contributor contributor = contributorDao.read("dev@elimu.ai");
            
            // Authenticate
            new CustomAuthenticationManager().authenticateUser(contributor);
            
            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);
            
            return "redirect:/content";
        } else {
            return null;
        }
    }
}
