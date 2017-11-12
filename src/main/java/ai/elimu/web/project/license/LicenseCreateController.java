package ai.elimu.web.project.license;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.logic.LicenseGenerator;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.License;
import ai.elimu.model.project.Project;
import ai.elimu.util.Mailer;
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
        license.setLicenseNumber(LicenseGenerator.generateLicenseNumber());
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
            sendLicenseInEmail(license);

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
    
    private void sendLicenseInEmail(License license) {
        String to = license.getLicenseEmail();
        String from = "elimu.ai <info@elimu.ai>";
        String subject = "License number";
        String title = "Your license is ready!";
        String firstName = ""; // TODO: store firstName/lastName when generating a new License
        String htmlText = "<p>Hi, " + firstName + "</p>";
        htmlText += "<p>We have prepared a license number for you so that you can download and use our software.</p>";
        htmlText += "<h2>License Details</h2>";
        htmlText += "<p>";
            htmlText += "E-mail: " + license.getLicenseEmail() + "<br />";
            htmlText += "Number: " + license.getLicenseNumber()+ "<br />";
        htmlText += "</p>";
        htmlText += "<h2>How to Start?</h2>";
        htmlText += "<ol>";
            htmlText += "<li>Install our Appstore application</li>";
            htmlText += "<li>Open Appstore and type your e-mail + license number</li>";
            htmlText += "<li>Download apps</li>";
        htmlText += "</ol>";
        htmlText += "<h2>Download Appstore</h2>";
        htmlText += "<p>At https://github.com/elimu-ai/appstore/releases you can download the latest version of our Appstore "
                + "application which helps you download the entire collection of educational Android apps. Start by clicking the button below:</p>";
        Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Download APK", "https://github.com/elimu-ai/appstore/releases");
    }
}
