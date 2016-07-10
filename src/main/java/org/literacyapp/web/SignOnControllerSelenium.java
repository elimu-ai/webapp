package org.literacyapp.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Role;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignOnControllerSelenium {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(value="/sign-on/test/role-contributor", method=RequestMethod.GET)
    public String handleRoleContributor(HttpServletRequest request, Model model) {
        logger.info("handleRoleContributor");
        
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            return "redirect:/sign-on";
        }
        
        Contributor contributor = new Contributor();
        contributor.setEmail("info+role-contributor@literacyapp.org");
        contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
        contributor.setRegistrationTime(Calendar.getInstance());	
				
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
	        	
        return "redirect:/content";
    }
}
