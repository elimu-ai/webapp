package ai.elimu.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.enums.Role;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignOnControllerSelenium {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private ContributorDao contributorDao;

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
                
        return "redirect:/content";
    }
}
