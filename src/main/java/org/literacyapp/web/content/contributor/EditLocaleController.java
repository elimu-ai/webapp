package org.literacyapp.web.content.contributor;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-locale")
public class EditLocaleController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        model.addAttribute("locales", Locale.values());
        
        // TODO: fetch from MailChimp and pre-fill
    	
        return "content/contributor/edit-locale";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam Locale locale,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        logger.info("locale: " + locale);
        
        if (locale == null) {
            model.addAttribute("errorCode", "NotNull.locale");
            model.addAttribute("locales", Locale.values());
            return "content/contributor/edit-locale";
        } else {        
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            contributor.setLocale(locale);
            contributorDao.update(contributor);
            session.setAttribute("contributor", contributor);

            return "redirect:/content";
        }
    }
}
