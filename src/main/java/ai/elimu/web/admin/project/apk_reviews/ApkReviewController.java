package ai.elimu.web.admin.project.apk_reviews;

import ai.elimu.dao.ApplicationDao;
import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.model.enums.admin.ApplicationVersionStatus;
import ai.elimu.util.Mailer;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller enabling approval/refusal of uploaded {@link ApplicationVersion}s.
 * <p>
 * The result of the review is sent via e-mail to the {@link Contributor} who uploaded the APK.
 */
@Controller
@RequestMapping("/admin/project/apk-reviews/{applicationVersionId}")
public class ApkReviewController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long applicationVersionId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        ApplicationVersion applicationVersion = applicationVersionDao.read(applicationVersionId);
        model.addAttribute("applicationVersion", applicationVersion);
        
        List<ApplicationVersionStatus> applicationVersionStatuses = new ArrayList<>();
        applicationVersionStatuses.add(ApplicationVersionStatus.APPROVED);
        applicationVersionStatuses.add(ApplicationVersionStatus.NOT_APPROVED);
        model.addAttribute("applicationVersionStatuses", applicationVersionStatuses);

        return "admin/project/apk-reviews/edit";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @PathVariable Long applicationVersionId,
            @RequestParam ApplicationVersionStatus applicationVersionStatus,
            @RequestParam String comment,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        logger.info("applicationVersionStatus: " + applicationVersionStatus);
        if (applicationVersionStatus == null) {
            // TODO: display error message
            return "redirect:/admin/project/apk-reviews/" + applicationVersionId;
        }
        
        logger.info("comment: " + comment);
        if ((applicationVersionStatus == ApplicationVersionStatus.NOT_APPROVED) 
                && StringUtils.isBlank(comment)) {
            // TODO: display error message
            return "redirect:/admin/project/apk-reviews/" + applicationVersionId;
        }
        
        ApplicationVersion applicationVersion = applicationVersionDao.read(applicationVersionId);
        applicationVersion.setApplicationVersionStatus(applicationVersionStatus);
        applicationVersionDao.update(applicationVersion);
        
        if (applicationVersionStatus == ApplicationVersionStatus.APPROVED) {
            // Update status of corresponding Application
            Application application = applicationVersion.getApplication();
            logger.info("application.getApplicationStatus(): " + application.getApplicationStatus());
            if (application.getApplicationStatus() != ApplicationStatus.ACTIVE) {
                application.setApplicationStatus(ApplicationStatus.ACTIVE);
                applicationDao.update(application);
            }
        }
        
        // Post review result in Slack
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            String text = URLEncoder.encode(
                    applicationVersion.getContributor().getFirstName() + ", your APK upload has been reviewed:\n" + 
                    "• Project: \"" + applicationVersion.getApplication().getAppGroup().getAppCategory().getProject().getName() + "\"\n" +
                    "• App Category: \"" + applicationVersion.getApplication().getAppGroup().getAppCategory().getName() + "\"\n" +
                    "• AppGroup: #" + applicationVersion.getApplication().getAppGroup().getId() + "\n" +
                    "• Package name: \"" + applicationVersion.getApplication().getPackageName() + "\"\n" + 
                    "• Version code: " + applicationVersion.getVersionCode() + "\n" +
                    "• Version name: \"" + applicationVersion.getVersionName()+ "\"\n" +
                    "• New APK status: " + applicationVersion.getApplicationVersionStatus() + "\n" +
                    "• Comment: \"" + (StringUtils.isBlank(comment) ? "" : comment) + "\"\n" +
                    "See ") + "http://elimu.ai/project/" + applicationVersion.getApplication().getAppGroup().getAppCategory().getProject().getId() + "/app-category/" + applicationVersion.getApplication().getAppGroup().getAppCategory().getId() + "/app-group/" + applicationVersion.getApplication().getAppGroup().getId() + "/app/" + applicationVersion.getApplication().getId() + "/edit";
            SlackApiHelper.postMessage("G6UR7UH2S", text, null, null);
        }
        
        // Send review result via e-mail
        String to = applicationVersion.getContributor().getEmail();
        String from = "elimu.ai <info@elimu.ai>";
        String subject = "[" + applicationVersion.getApplication().getAppGroup().getAppCategory().getProject().getName() + "] Your APK upload has been reviewed";
        String title = "APK review complete";
        String firstName = StringUtils.isBlank(applicationVersion.getContributor().getFirstName()) ? "" : applicationVersion.getContributor().getFirstName();
        String htmlText = "<p>Hi, " + firstName + "</p>";
        htmlText += "<p>Your APK upload has been reviewed:</p>";
        htmlText += "<ul>";
            htmlText += "<li>Project: \"" + applicationVersion.getApplication().getAppGroup().getAppCategory().getProject().getName() + "\"</li>";
            htmlText += "<li>App Category: \"" + applicationVersion.getApplication().getAppGroup().getAppCategory().getName() + "\"</li>";
            htmlText += "<li>AppGroup: #" + applicationVersion.getApplication().getAppGroup().getId() + "</li>";
            htmlText += "<li>Package name: \"" + applicationVersion.getApplication().getPackageName() + "\"</li>";
            htmlText += "<li>Version code: " + applicationVersion.getVersionCode() + "</li>";
            htmlText += "<li>Version name: \"" + applicationVersion.getVersionName()+ "\"</li>";
            htmlText += "<li>New APK status: " + applicationVersion.getApplicationVersionStatus() + "</li>";
            htmlText += "<li>Comment: \"" + (StringUtils.isBlank(comment) ? "" : comment) + "\"</li>";
        htmlText += "</ul>";
        htmlText += "<h2>Application</h2>";
        htmlText += "<p>See http://elimu.ai/project/" + applicationVersion.getApplication().getAppGroup().getAppCategory().getProject().getId() + "/app-category/" + applicationVersion.getApplication().getAppGroup().getAppCategory().getId() + "/app-group/" + applicationVersion.getApplication().getAppGroup().getId() + "/app/" + applicationVersion.getApplication().getId() + "/edit</p>";
        Mailer.sendHtml(to, null, from, subject, title, htmlText);
        
        return "redirect:/admin/project/apk-reviews";
    }
}
