package ai.elimu.web.project.app_collection;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.License;
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
@RequestMapping("/project/{projectId}/app-collection/edit")
public class AppCollectionEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;
    
    @Autowired
    private LicenseDao licenseDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId,
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        // Needed by breadcrumbs
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCollection appCollection = appCollectionDao.read(id);
        model.addAttribute("appCollection", appCollection);
        
        List<License> licenses = licenseDao.readAll(appCollection);
        model.addAttribute("licenses", licenses);

        return "project/app-collection/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @PathVariable Long projectId,
            @Valid AppCollection appCollection,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        // Disallow app collections with identical name
        Project project = projectDao.read(projectId);
        List<AppCollection> existingAppCollections = appCollectionDao.readAll(project);
        for (AppCollection existingAppCollection : existingAppCollections) {
            if (existingAppCollection.getName().equals(appCollection.getName()) && !existingAppCollection.getId().equals(appCollection.getId())) {
                result.rejectValue("name", "NonUnique");
                break;
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            
            model.addAttribute("appCollection", appCollection);
            
            List<License> licenses = licenseDao.readAll(appCollection);
            model.addAttribute("licenses", licenses);
            
            return "project/app-collection/edit";
        } else {
            appCollectionDao.update(appCollection);

            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                // Notify project members in Slack
                Contributor contributor = (Contributor) session.getAttribute("contributor");
                String text = URLEncoder.encode(contributor.getFirstName() + " just updated an App Collection:\n" +
                    "• Project: \"" + project.getName() + "\"\n" +
                    "• App Collection: \"" + appCollection.getName() + "\"\n" +
                    "See ") + "http://elimu.ai/project/" + project.getId() + "/app-collection/edit/" + appCollection.getId();
                SlackApiHelper.postMessage("G6UR7UH2S", text, null, null);
            }
            
            return "redirect:/project/" + project.getId();
        }
    }
}
