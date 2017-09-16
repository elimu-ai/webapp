package ai.elimu.web.admin.project;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.project.Project;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/project/create")
public class AdminProjectCreateController {
    
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
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 Contributor contributor = (Contributor) session.getAttribute("contributor");
                 String text = URLEncoder.encode(
                         contributor.getFirstName() + " just added a new project: \"" + project.getName() + "\""
                 );
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage("G6UR7UH2S", text, iconUrl, null);
             }
            
            return "redirect:/admin/project/list#" + project.getId();
        }
    }
}
