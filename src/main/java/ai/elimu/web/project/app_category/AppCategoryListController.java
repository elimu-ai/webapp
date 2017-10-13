package ai.elimu.web.project.app_category;

import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-category")
public class AppCategoryListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String handlRequest(Model model, @PathVariable Long projectId, HttpSession session) {
    	logger.info("handleRequest");
        
        logger.info("projectId: " + projectId);
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        List<AppCollection> appCollections = appCollectionDao.readAll(project);
        model.addAttribute("appCollections", appCollections);
        
        return "project/app-category/list";
    }
}
