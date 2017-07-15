package ai.elimu.web.content.community;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/community/contributors")
public class ContributorsController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request, HttpSession session, Principal principal, Model model) {
    	logger.info("handleRequest");
        
        List<Contributor> contributors = contributorDao.readAllOrderedDesc();
        model.addAttribute("contributors", contributors);
    	
        return "content/community/contributors";
    }
}
