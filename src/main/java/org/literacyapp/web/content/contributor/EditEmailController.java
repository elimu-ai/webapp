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

@Controller
@RequestMapping("/content/contributor/edit-email")
public class EditEmailController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleEditEmailRequest() {
    	logger.info("handleEditEmailRequest");
    	
        return "content/contributor/edit-email";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleEditEmailSubmit(
            HttpSession session,
            @RequestParam String email,
            Model model) {
    	logger.info("handleEditEmailSubmit");
        
        if (!EmailValidator.getInstance().isValid(email)) {
            return "content/contributor/edit-email";
        }
        
        // TODO: check if e-mail already is used by existing Contributor
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        contributor.setEmail(email);
        
        Contributor existingContributor = contributorDao.read(contributor.getEmail());
        if (existingContributor != null) {
            contributorDao.update(contributor);
        } else {
            // Redirected from SignOnControllerGitHub because of missing e-mail
            contributorDao.create(contributor);
        }
        
        session.setAttribute("contributor", contributor);
    	
        return "redirect:/content";
    }
}
