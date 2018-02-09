package ai.elimu.web.project.app;

import ai.elimu.dao.AppCategoryDao;
import ai.elimu.dao.AppGroupDao;

import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppGroup;
import ai.elimu.model.project.Project;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-category/{appCategoryId}/app-group/{appGroupId}/app/{applicationId}/edit")
public class AppEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @Autowired
    private AppGroupDao appGroupDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId,
            @PathVariable Long appCategoryId,
            @PathVariable Long appGroupId,
            @PathVariable Long applicationId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        model.addAttribute("appCategory", appCategory);
        
        AppGroup appGroup = appGroupDao.read(appGroupId);
        model.addAttribute("appGroup", appGroup);
        
        Application application = applicationDao.read(applicationId);
        model.addAttribute("application", application);
        
        List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
        model.addAttribute("applicationVersions", applicationVersions);
        
        model.addAttribute("applicationStatuses", ApplicationStatus.values());

        return "project/app/edit";
    }
}
