package ai.elimu.web.content.contributor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-language")
public class EditLanguageController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        model.addAttribute("languages", Language.values());
    	
        return "content/contributor/edit-language";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam Language language,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        logger.info("language: " + language);
        
        if (language == null) {
            model.addAttribute("errorCode", "NotNull.language");
            model.addAttribute("languages", Language.values());
            return "content/contributor/edit-language";
        } else {        
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            contributor.setLanguage(language);
            contributorDao.update(contributor);
            session.setAttribute("contributor", contributor);

            return "redirect:/content";
        }
    }
}
