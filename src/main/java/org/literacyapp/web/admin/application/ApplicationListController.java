package org.literacyapp.web.admin.application;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.Application;
import org.literacyapp.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/list")
public class ApplicationListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("contributor.getLocale(): " + contributor.getLocale());
        model.addAttribute("locale", contributor.getLocale());
        
        List<Application> applications = applicationDao.readAll(contributor.getLocale());
        model.addAttribute("applications", applications);

        return "admin/application/list";
    }
}
