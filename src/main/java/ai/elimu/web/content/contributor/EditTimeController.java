package ai.elimu.web.content.contributor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-time")
public class EditTimeController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");

        return "content/contributor/edit-time";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam Integer timePerWeek,
            Model model) {
    	logger.info("handleSubmit");
        
        logger.info("timePerWeek: " + timePerWeek);
        // TODO: validate timePerWeek
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        contributor.setTimePerWeek(timePerWeek);
        contributorDao.update(contributor);
        session.setAttribute("contributor", contributor);
    	
        return "redirect:/content";
    }
}
