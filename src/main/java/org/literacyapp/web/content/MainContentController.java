package org.literacyapp.web.content;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content")
public class MainContentController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            HttpServletRequest request, 
            HttpSession session, 
            Principal principal, 
            Model model) {
    	logger.info("handleRequest");
        
        // Check if the Contributor has not yet provided all required details
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        if (StringUtils.isBlank(contributor.getEmail())) {
            return "redirect:/content/contributor/add-email";
        } else if (StringUtils.isBlank(contributor.getFirstName()) || StringUtils.isBlank(contributor.getLastName())) {
            return "redirect:/content/contributor/edit-name";
        } else if (contributor.getLocale() == null) {
            return "redirect:/content/contributor/edit-locale";
        } else if ((contributor.getTeams() == null) || contributor.getTeams().isEmpty()) {
            return "redirect:/content/contributor/edit-teams";
        } else if (StringUtils.isBlank(contributor.getMotivation())) {
            return "redirect:/content/contributor/edit-motivation";
        } else if (contributor.getTimePerWeek() == null) {
            return "redirect:/content/contributor/edit-time";
        }
        
        model.addAttribute("numberCount", numberDao.readCount(contributor.getLocale()));
        model.addAttribute("letterCount", letterDao.readCount(contributor.getLocale()));
        model.addAttribute("wordCount", wordDao.readCount(contributor.getLocale()));
    	
        return "content/main";
    }
}
