package org.literacyapp.web.admin.application;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.dao.ApplicationVersionDao;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.admin.ApplicationVersion;
import org.literacyapp.model.enums.LiteracySkill;
import org.literacyapp.model.enums.NumeracySkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/edit")
public class ApplicationEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long id,
            Model model
    ) {
    	logger.info("handleRequest");
        
        Application application = applicationDao.read(id);
        model.addAttribute("application", application);
        
        List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
        model.addAttribute("applicationVersions", applicationVersions);
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());

        return "admin/application/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Application application,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        if (result.hasErrors()) {
            model.addAttribute("application", application);
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            return "admin/application/edit";
        } else {
            applicationDao.update(application);
            return "redirect:/admin/application/list";
        }
    }
}
