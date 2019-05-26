package ai.elimu.web.project.app_group;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.project.AppCategoryDao;
import ai.elimu.dao.project.AppGroupDao;
import ai.elimu.dao.project.ProjectDao;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppGroup;
import ai.elimu.model.project.Project;
import ai.elimu.rest.service.project.ProjectJsonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-category/{appCategoryId}/app-group/create")
public class AppGroupCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectJsonService projectJsonService;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @Autowired
    private AppGroupDao appGroupDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId, 
            @PathVariable Long appCategoryId, 
            Model model, 
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        // Needed by breadcrumbs
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        model.addAttribute("appCategory", appCategory);
        
        AppGroup appGroup = new AppGroup();
        model.addAttribute("appGroup", appGroup);

        return "project/app-group/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @PathVariable Long projectId,
            @PathVariable Long appCategoryId,
            @Valid AppGroup appGroup,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        model.addAttribute("appCategory", appCategory);
        
        List<AppGroup> appGroups = appCategory.getAppGroups();
        if (!appGroups.isEmpty()) {
            // Do not allow creation of a new AppGroup if an empty one already exists
            for (AppGroup existingAppGroup : appGroups) {
                if (existingAppGroup.getApplications().isEmpty()) {
                    result.reject("EmptyAppGroup");
                    break;
                }
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("appGroup", appGroup);
            return "project/app-group/create";
        } else {
            appGroup.setAppCategory(appCategory);
            appGroupDao.create(appGroup);
            
            appCategory.getAppGroups().add(appGroup);
            appCategoryDao.update(appCategory);
            
            // Refresh REST API cache
//            projectJsonService.refreshApplicationsInAppCollection(appCollection);
            projectJsonService.refreshApplicationsInAppCollection();
            
            return "redirect:/project/" + project.getId() + "/app-category/" + appCategory.getId() + "/app-group/list";
        }
    }
}
