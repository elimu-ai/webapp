package ai.elimu.web.project.license;

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
@RequestMapping("/project/{projectId}/app-collection/{appCollectionId}/license/create")
public class LicenseCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;
    
    @Autowired
    private LicenseDao licenseDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId, 
            @PathVariable Long appCollectionId, 
            Model model, 
            HttpSession session
    ) {
    	logger.info("handleRequest");
        
        // Needed by breadcrumbs
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCollection appCollection = appCollectionDao.read(appCollectionId);
        model.addAttribute("appCollection", appCollection);
        
        License license = new License();
        license.setAppCollection(appCollection);
        model.addAttribute("license", license);

        return "project/license/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @PathVariable Long projectId,
            @PathVariable Long appCollectionId,
            @Valid License license,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        // Needed by breadcrumbs and Slack post
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCollection appCollection = appCollectionDao.read(appCollectionId);
        model.addAttribute("appCollection", appCollection);
        
        // Disallow Licenses with identical e-mail
        List<License> existingLicenses = licenseDao.readAll(appCollection);
        for (License existingLicence : existingLicenses) {
            if (existingLicence.getLicenseEmail().equals(license.getLicenseEmail())) {
                result.rejectValue("name", "NonUnique");
                break;
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("license", license);
            return "project/license/create";
        } else {
            licenseDao.create(license);

            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                // Notify project members in Slack
                Contributor contributor = (Contributor) session.getAttribute("contributor");
                String text = URLEncoder.encode(
                    contributor.getFirstName() + " just added a new License:\n" +
                    "• Project: \"" + project.getName() + "\"\n" +
                    "• App Collection: \"" + appCollection.getName() + "\"\n" +
                    "• E-mail: \"" + license.getLicenseEmail() + "\"\n" +
                    "See ") + "http://elimu.ai/project/" + project.getId() + "/app-collection/edit/" + appCollection.getId();
                SlackApiHelper.postMessage("G6UR7UH2S", text, null, null);
            }
            
            return "redirect:/project/" + project.getId() + "/app-collection/edit/" + appCollection.getId();
        }
    }
}
