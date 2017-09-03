package ai.elimu.web.admin.project;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/project/create")
public class ProjectCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Project project = new Project();
        model.addAttribute("project", project);

        return "admin/project/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Project project,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Project existingProject = projectDao.read(project.getName());
        if (existingProject != null) {
            result.rejectValue("name", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            return "admin/project/create";
        } else {
            projectDao.create(project);
            return "redirect:/admin/project/list#" + project.getId();
        }
    }
}
