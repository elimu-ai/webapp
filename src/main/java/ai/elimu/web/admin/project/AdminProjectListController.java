package ai.elimu.web.admin.project;

import ai.elimu.dao.ProjectDao;
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
@RequestMapping("/admin/project/list")
public class AdminProjectListController {

    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        List<Project> projects = projectDao.readAll();
        model.addAttribute("projects", projects);

        return "admin/project/list";
    }
}
