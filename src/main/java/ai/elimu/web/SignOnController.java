package ai.elimu.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.Role;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sign-on")
public class SignOnController {

    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.debug("handleRequest");
        
        Language[] supportedLanguages = Language.values();
        model.addAttribute("supportedLanguages", supportedLanguages);
    	
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
            // Create and store test user in database
            Contributor contributor = contributorDao.read("test@elimu.ai");
            if (contributor == null) {
                contributor = new Contributor();
                contributor.setEmail("test@elimu.ai");
                contributor.setFirstName("Test");
                contributor.setLastName("Contributor");
                contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR)));
                contributor.setRegistrationTime(Calendar.getInstance());
                contributorDao.create(contributor);
            }
            
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
