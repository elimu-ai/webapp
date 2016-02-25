package org.literacyapp.web.content.contributor;

import javax.servlet.http.HttpSession;
import org.apache.commons.validator.EmailValidator;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// Redirected from SignOnControllerGitHub because of missing e-mail
@Controller
@RequestMapping("/content/contributor/add-email")
public class AddEmailController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");
    	
        return "content/contributor/add-email";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam String email,
            Model model) {
    	logger.info("handleSubmit");
        
        if (!EmailValidator.getInstance().isValid(email)) {
            // TODO: display error message
            return "content/contributor/add-email";
        }
        
        Contributor existingContributor = contributorDao.read(email);
        if (existingContributor != null) {
            // TODO: display error message
            return "content/contributor/add-email";
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        contributor.setEmail(email);
        contributorDao.create(contributor);
        
        session.setAttribute("contributor", contributor);
    	
        return "redirect:/content";
    }
}
