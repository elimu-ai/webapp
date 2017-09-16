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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project")
public class ProjectListController {

    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<Project> projects = projectDao.read(contributor);
        model.addAttribute("projects", projects);

        return "redirect:/project/list";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String handleListRequest(Model model, HttpSession session) {
    	logger.info("handleListRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<Project> projects = projectDao.read(contributor);
        model.addAttribute("projects", projects);

        return "project/list";
    }
}
