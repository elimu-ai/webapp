package org.literacyapp.web.admin.application;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.Application;
import org.literacyapp.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/create")
public class ApplicationCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Application application = new Application();
        application.setLocale(contributor.getLocale());
        model.addAttribute("application", application);

        return "admin/application/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Application application,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Application existingApplication = applicationDao.readByPackageName(application.getLocale(), application.getPackageName());
        if (existingApplication != null) {
            result.rejectValue("packageName", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("application", application);
            return "admin/application/create";
        } else {
            applicationDao.create(application);
            return "redirect:/admin/application/list";
        }
    }
}
