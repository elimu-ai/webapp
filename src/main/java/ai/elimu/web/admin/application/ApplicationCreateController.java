package ai.elimu.web.admin.application;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.admin.Application;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/create")
public class ApplicationCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Application application = new Application();
        application.setApplicationStatus(ApplicationStatus.MISSING_APK);
        model.addAttribute("application", application);
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());

        return "admin/application/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Application application,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Application existingApplication = applicationDao.readByPackageName(application.getPackageName());
        if (existingApplication != null) {
            result.rejectValue("packageName", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("application", application);
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            return "admin/application/create";
        } else {
            applicationDao.create(application);
            return "redirect:/admin/application/list#" + application.getId();
        }
    }
}
