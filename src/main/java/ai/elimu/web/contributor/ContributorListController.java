package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contributor/list")
public class ContributorListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @GetMapping
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        model.addAttribute("contributors", contributorDao.readAllOrderedDesc());

        return "contributor/list";
    }
}
