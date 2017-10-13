package ai.elimu.web.project.app_collection;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/{projectId}/app-collection/create")
public class AppCollectionCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId, 
            Model model, 
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCollection appCollection = new AppCollection();
        appCollection.setProject(project);
        model.addAttribute("appCollection", appCollection);

        return "project/app-collection/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid AppCollection appCollection,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Project project = appCollection.getProject();
        model.addAttribute("project", project);
        
        // Disallow app collections with identical name
        List<AppCollection> existingAppCollections = appCollectionDao.readAll(project);
        for (AppCollection existingAppCollection : existingAppCollections) {
            if (existingAppCollection.getName().equals(appCollection.getName())) {
                result.rejectValue("name", "NonUnique");
                break;
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("appCollection", appCollection);
            return "project/app-collection/create";
        } else {
            appCollectionDao.create(appCollection);

            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                // Notify project members in Slack
                Contributor contributor = (Contributor) session.getAttribute("contributor");
                String text = URLEncoder.encode(
                    contributor.getFirstName() + " just added a new App Collection:\n" +
                    "• Project: \"" + appCollection.getProject().getName() + "\"\n" +
                    "• App Collection: \"" + appCollection.getName() + "\"\n" +
                    "See ") + "http://elimu.ai/project/" + appCollection.getProject().getId();
                SlackApiHelper.postMessage("G6UR7UH2S", text, null, null);
            }
            
            return "redirect:/project/" + appCollection.getProject().getId();
        }
    }
}
