package org.literacyapp.web.content;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.Image;
import org.literacyapp.model.Number;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content")
public class MainContentController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private ImageDao imageDao;

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
        }
        
        List<Number> numbers = numberDao.readLatest(contributor.getLocale());
        model.addAttribute("numbers", numbers);
        
        List<Image> images = imageDao.readLatest(contributor.getLocale());
        model.addAttribute("images", images);
    	
        return "content/main";
    }
}
