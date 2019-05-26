package ai.elimu.web.project.license;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.project.AppCollectionDao;
import ai.elimu.dao.project.LicenseDao;
import ai.elimu.dao.project.ProjectDao;
import ai.elimu.logic.project.LicenseGenerator;
import ai.elimu.model.Contributor;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.License;
import ai.elimu.model.project.Project;
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
        license.setLicenseNumber(LicenseGenerator.generateLicenseNumber());
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
        
        // Needed by breadcrumbs
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCollection appCollection = appCollectionDao.read(appCollectionId);
        model.addAttribute("appCollection", appCollection);
        
        // Disallow Licenses with identical e-mail
        List<License> existingLicenses = licenseDao.readAll(appCollection);
        for (License existingLicence : existingLicenses) {
            if (existingLicence.getLicenseEmail().equals(license.getLicenseEmail())) {
                result.rejectValue("licenseEmail", "NonUnique");
                break;
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("license", license);
            return "project/license/create";
        } else {
            licenseDao.create(license);
            
            // Send license information via e-mail
            Contributor contributor = (Contributor) session.getAttribute("contributor");
//            sendLicenseInEmail(license, contributor);
            
            return "redirect:/project/" + project.getId() + "/app-collection/edit/" + appCollection.getId();
        }
    }
}
