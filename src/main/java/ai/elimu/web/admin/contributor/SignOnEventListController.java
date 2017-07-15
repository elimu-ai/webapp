package ai.elimu.web.admin.contributor;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.contributor.SignOnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/sign-on-event/list")
public class SignOnEventListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        List<SignOnEvent> signOnEvents = signOnEventDao.readAllOrderedDesc();
        model.addAttribute("signOnEvents", signOnEvents);

        return "admin/sign-on-event/list";
    }
}
