package ai.elimu.web.project.app_group;

import ai.elimu.dao.AppCategoryDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.Project;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-category/{appCategoryId}/app-group")
public class AppGroupListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String handlRequest(
            Model model,
            @PathVariable Long projectId,
            @PathVariable Long appCategoryId,
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        logger.info("projectId: " + projectId);
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        logger.info("appCategoryId: " + appCategoryId);
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        model.addAttribute("appCategory", appCategory);
        
        return "project/app-group/list";
    }
}
