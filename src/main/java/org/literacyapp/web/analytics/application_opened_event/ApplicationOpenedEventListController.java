package org.literacyapp.web.analytics.application_opened_event;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationOpenedEventDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.analytics.ApplicationOpenedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/application-opened-event/list")
public class ApplicationOpenedEventListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationOpenedEventDao applicationOpenedEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(contributor.getLocale());
        model.addAttribute("applicationOpenedEvents", applicationOpenedEvents);

        return "analytics/application-opened-event/list";
    }
}
