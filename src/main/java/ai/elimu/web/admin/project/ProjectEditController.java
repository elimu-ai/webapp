package ai.elimu.web.admin.project;

import ai.elimu.dao.ContributorDao;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.project.Project;
import ai.elimu.util.Mailer;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/project/edit")
public class ProjectEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            Model model,
            @PathVariable Long id
    ) {
    	logger.info("handleRequest");
        
        Project project = projectDao.read(id);
        model.addAttribute("project", project);
        
        List<Contributor> contributors = contributorDao.readAll();
        model.addAttribute("managers", contributors);

        return "admin/project/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Project project,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Project existingProject = projectDao.read(project.getName());
        if ((existingProject != null) && !existingProject.getId().equals(project.getId())) {
            result.rejectValue("name", "NonUnique");
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            
            List<Contributor> contributors = contributorDao.readAll();
            model.addAttribute("managers", contributors);
            
            return "admin/project/edit";
        } else {
            projectDao.update(project);
            
            // Send invitation e-mail to newly added managers
            for (Contributor manager : project.getManagers()) {
                if ((existingProject.getManagers() == null) || !existingProject.getManagers().contains(manager)) {
                    String to = manager.getEmail();
                    String from = "elimu.ai <info@elimu.ai>";
                    String subject = "You have been added as a manager";
                    String title = project.getName();
                    String firstName = StringUtils.isBlank(manager.getFirstName()) ? "" : manager.getFirstName();
                    String htmlText = "<p>Hi, " + firstName + "</p>";
                    htmlText += "<p>" + contributor.getFirstName() + " has added you as a manager of the \"" + project.getName() + "\" project.</p>";
                    htmlText += "<p>To create a new app collection, click the button below:</p>";
                    String buttonUrl = "http://elimu.ai/project";
                    if (EnvironmentContextLoaderListener.env == Environment.TEST) {
                        buttonUrl = "http://test.elimu.ai/project";
                    }
                    Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open project", buttonUrl);
                }
            }
            
            return "redirect:/admin/project/list#" + project.getId();
        }
    }
}
