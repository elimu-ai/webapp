package ai.elimu.web.project;

import ai.elimu.dao.AppCategoryDao;
import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.AppGroupDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.AppGroup;
import ai.elimu.model.project.License;
import ai.elimu.model.project.Project;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * List custom projects.
 * <p />
 * Only projects where the current contributer is added as a manager will be listed.
 */
@Controller
@RequestMapping("/project")
public class ProjectListController {

    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @Autowired
    private AppGroupDao appGroupDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;
    
    @Autowired
    private LicenseDao licenseDao;
    
    /**
     * Redirect to list of projects.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        return "redirect:/project/list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String handleListRequest(Model model, HttpSession session) {
    	logger.info("handleListRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Project> projects = projectDao.read(contributor);
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            if (projects.isEmpty()) {
                // To ease development, auto-generate Projects
                projects = generateProjects(contributor);
            }
        }
        
        model.addAttribute("projects", projects);

        return "project/list";
    }
    
    /**
     * Redirect to specific project.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleProjectRequest(Model model, @PathVariable Long id, HttpSession session) {
    	logger.info("handleProjectRequest");
        
        return "redirect:/project/" + id + "/app-category/list";
    }
    
    private List<Project> generateProjects(Contributor contributor) {
        logger.info("generateProjects");
        List<Project> projects = new ArrayList<>();
        
        Project project1 = new Project();
        project1.setName("Project #1");
        project1.setManagers(Arrays.asList(contributor));
        projectDao.create(project1);
        projects.add(project1);
        
        AppCategory appCategory1 = new AppCategory();
        appCategory1.setName("App category #1");
        appCategoryDao.create(appCategory1);
        
        List<AppCategory> appCategories = new ArrayList<>();
        appCategories.add(appCategory1);
        project1.setAppCategories(appCategories);
        projectDao.update(project1);
        
        AppGroup appGroup1 = new AppGroup();
        appGroupDao.create(appGroup1);
        
        List<AppGroup> appGroups = new ArrayList<>();
        appGroups.add(appGroup1);
        appCategory1.setAppGroups(appGroups);
        appCategoryDao.update(appCategory1);
        
        AppCollection appCollection1 = new AppCollection();
        appCollection1.setName("App collection #1");
        appCollection1.setProject(project1);
        appCollection1.setAppCategories(Arrays.asList(appCategory1));
        appCollectionDao.create(appCollection1);
        
        License license1 = new License();
        license1.setAppCollection(appCollection1);
        license1.setLicenseEmail("info@elimu.ai");
        license1.setLicenseNumber("bddf-d8f4-2adf-a365");
        licenseDao.create(license1);
        
        return projects;
    }
}
