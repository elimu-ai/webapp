package org.literacyapp.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.dao.SignOnEventDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.contributor.SignOnEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.Role;
import org.literacyapp.model.enums.Team;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
        contributor.setEmail("info+role-" + role + "@literacyapp.org");
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
        signOnEventDao.create(signOnEvent);
	        	
        return "redirect:/content";
    }
}
