package ai.elimu.web.admin.application;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.admin.Application;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
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
        
        Application existingApplication = applicationDao.readByPackageName(application.getLocale(), application.getPackageName());
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
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 Contributor contributor = (Contributor) session.getAttribute("contributor");
                 String text = URLEncoder.encode(
                         contributor.getFirstName() + " just added a new Application:\n" + 
                         "• Language: " + application.getLocale().getLanguage() + "\n" + 
                         "• Package name: \"" + application.getPackageName() + "\"\n" + 
                         "• Literacy skills: " + application.getLiteracySkills() + "\n" + 
                         "• Numeracy skills: " + application.getNumeracySkills());
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.DEVELOPMENT), text, iconUrl, null);
             }
            
            return "redirect:/admin/application/list";
        }
    }
}
