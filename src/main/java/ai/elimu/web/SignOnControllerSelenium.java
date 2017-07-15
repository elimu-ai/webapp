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
import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.Provider;
import ai.elimu.model.enums.Role;
import ai.elimu.model.enums.Team;
import ai.elimu.util.CookieHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignOnControllerSelenium {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    @RequestMapping(value="/sign-on/test/role-{role}", method=RequestMethod.GET)
    public String handleRequest(
            @PathVariable Role role,
            HttpServletRequest request,
            Model model
    ) {
        logger.info("handleRequest");
        
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            return "redirect:/sign-on";
        }
        
        logger.info("role: " + role);
        
        Contributor contributor = new Contributor();
        contributor.setEmail("info+role-" + role + "@elimu.ai");
        contributor.setRoles(new HashSet<>(Arrays.asList(role)));
        contributor.setRegistrationTime(Calendar.getInstance());
        contributor.setFirstName("TestRole");
        contributor.setLastName(role.toString());
        contributor.setLocale(Locale.EN);
        contributor.setTeams(new HashSet<>(Arrays.asList(Team.TESTING)));
        contributor.setMotivation("Regression testing as " + role);
				
        Contributor existingContributor = contributorDao.read(contributor.getEmail());
        logger.info("existingContributor: " + existingContributor);
        if (existingContributor != null) {
            contributor = existingContributor;
        } else {
            contributorDao.create(contributor);
            logger.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
        }

        // Authenticate
        new CustomAuthenticationManager().authenticateUser(contributor);

        // Add Contributor object to session
        request.getSession().setAttribute("contributor", contributor);
        
        SignOnEvent signOnEvent = new SignOnEvent();
        signOnEvent.setContributor(contributor);
        signOnEvent.setCalendar(Calendar.getInstance());
        signOnEvent.setServerName(request.getServerName());
        signOnEvent.setProvider(Provider.SELENIUM);
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
    }
}
