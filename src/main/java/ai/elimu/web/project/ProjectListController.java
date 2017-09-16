package ai.elimu.web.project;

import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
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
}
