package ai.elimu.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.contributor.SignOnEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Provider;
import ai.elimu.model.enums.Role;
import ai.elimu.model.enums.Team;
import ai.elimu.util.CookieHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sign-on")
public class SignOnController {

    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(ModelMap model) {
    	logger.debug("handleRequest");
        
        model.addAttribute("teams", Team.values());
    	
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
                contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR, Role.PROJECT_MANAGER)));
                contributor.setRegistrationTime(Calendar.getInstance());
                contributorDao.create(contributor);
            }
            
            // Authenticate
            new CustomAuthenticationManager().authenticateUser(contributor);
            
            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);
            
            SignOnEvent signOnEvent = new SignOnEvent();
            signOnEvent.setContributor(contributor);
            signOnEvent.setCalendar(Calendar.getInstance());
            signOnEvent.setServerName(request.getServerName());
            signOnEvent.setProvider(Provider.OFFLINE);
            signOnEvent.setRemoteAddress(request.getRemoteAddr());
            signOnEvent.setUserAgent(StringUtils.abbreviate(request.getHeader("User-Agent"), 1000));
            signOnEvent.setReferrer(CookieHelper.getReferrer(request));
            signOnEvent.setUtmSource(CookieHelper.getUtmSource(request));
            signOnEvent.setUtmMedium(CookieHelper.getUtmMedium(request));
            signOnEvent.setUtmCampaign(CookieHelper.getUtmCampaign(request));
            signOnEvent.setUtmTerm(CookieHelper.getUtmTerm(request));
            signOnEvent.setReferralId(CookieHelper.getReferralId(request));
            signOnEventDao.create(signOnEvent);
            
            return "redirect:/content";
        } else {
            return null;
        }
    }
}
