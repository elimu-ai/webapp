package ai.elimu.web.admin.project;

import ai.elimu.dao.ContributorDao;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.project.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.model.project.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/project/edit")
public class AdminProjectEditController {
    
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
            if (project.getManagers() != null) {
                for (Contributor manager : project.getManagers()) {
                    boolean isManagerAddedAlready = false;
                    if ((existingProject != null) && (existingProject.getManagers() != null)) {
                        for (Contributor existingManager : existingProject.getManagers()) {
                            if (existingManager.getId().equals(manager.getId())) {
                                isManagerAddedAlready = true;
                                break;
                            }
                        }
                    }
                    logger.info("isManagerAddedAlready: " + isManagerAddedAlready + ", manager.getEmail(): " + manager.getEmail());
                    if (!isManagerAddedAlready) {
                        // Update role so that the contributor can access the /project page
                        manager.getRoles().add(Role.PROJECT_MANAGER);
                        contributorDao.update(manager);
                    }
                }
            }
            
            return "redirect:/admin/project/list#" + project.getId();
        }
    }
}
