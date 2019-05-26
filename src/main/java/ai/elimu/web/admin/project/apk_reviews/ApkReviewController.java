package ai.elimu.web.admin.project.apk_reviews;

import ai.elimu.dao.ApplicationDao;
import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.model.enums.admin.ApplicationVersionStatus;
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
        
        return "redirect:/admin/project/apk-reviews";
    }
}
