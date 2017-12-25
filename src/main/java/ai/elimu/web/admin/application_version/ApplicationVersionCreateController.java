package ai.elimu.web.admin.application_version;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import net.dongliu.apk.parser.ByteArrayApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/admin/application-version/create")
public class ApplicationVersionCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @RequestParam Long applicationId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        logger.info("applicationId: " + applicationId);
        Application application = applicationDao.read(applicationId);
        
        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setApplication(application);
        model.addAttribute("applicationVersion", applicationVersion);

        return "admin/application-version/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            ApplicationVersion applicationVersion,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        logger.info("applicationVersion.getVersionCode(): " + applicationVersion.getVersionCode());
        if (applicationVersion.getVersionCode() == null) {
            result.rejectValue("versionCode", "NotNull");
        } else {
            // Verify that the versionCode is higher than previous ones
            List<ApplicationVersion> existingApplicationVersions = applicationVersionDao.readAll(applicationVersion.getApplication());
            for (ApplicationVersion existingApplicationVersion : existingApplicationVersions) {
                if (existingApplicationVersion.getVersionCode() >= applicationVersion.getVersionCode()) {
                    result.rejectValue("versionCode", "TooLow");
                    break;
                }
            }
        }
        
        if (multipartFile.isEmpty()) {
            result.rejectValue("bytes", "NotNull");
        } else {
            try {
                byte[] bytes = multipartFile.getBytes();
                if (applicationVersion.getBytes() != null) {
                    String originalFileName = multipartFile.getOriginalFilename();
                    logger.info("originalFileName: " + originalFileName);
                    if (!originalFileName.endsWith(".apk")) {
                        result.rejectValue("bytes", "typeMismatch");
                    }

                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    applicationVersion.setContentType(contentType);
                    
                    applicationVersion.setBytes(bytes);
                    
                    Integer fileSizeInKb = bytes.length / 1024;
                    logger.info("fileSizeInKb: " + fileSizeInKb + " (" + (fileSizeInKb / 1024) + "MB)");
                    applicationVersion.setFileSizeInKb(fileSizeInKb);
                    
                    String checksumMd5 = ChecksumHelper.calculateMD5(bytes);
                    logger.info("checksumMd5: " + checksumMd5);
                    applicationVersion.setChecksumMd5(checksumMd5);
                    
                    ByteArrayApkFile byteArrayApkFile = new ByteArrayApkFile(bytes);
                    ApkMeta apkMeta = byteArrayApkFile.getApkMeta();
                    
                    String versionName = apkMeta.getVersionName();
                    logger.info("versionName: " + versionName);
                    applicationVersion.setVersionName(versionName);
                    
                    String label = apkMeta.getLabel();
                    logger.info("label: " + label);
                    applicationVersion.setLabel(label);
                    
                    byte[] icon = byteArrayApkFile.getIconFile().getData();
                    logger.info("icon.length: " + (icon.length / 1024) + "kB");
                    applicationVersion.setIcon(icon);
                    
                    // TODO: auto-detect packageName, versionCode, minSdk
                } else {
                    result.rejectValue("bytes", "NotNull");
                }
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("applicationVersion", applicationVersion);
            return "admin/application-version/create";
        } else {
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            applicationVersion.setContributor(contributor);
            applicationVersion.setTimeUploaded(Calendar.getInstance());
            applicationVersionDao.create(applicationVersion);
            
            // Update the Application entity to reflect the latest changes
            Application application = applicationVersion.getApplication();
            if (application.getApplicationStatus() == ApplicationStatus.MISSING_APK) {
                // If first APK file, change status of application to "ACTIVE"
                application.setApplicationStatus(ApplicationStatus.ACTIVE);
            }
            applicationDao.update(application);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                         contributor.getFirstName() + " just uploaded a new APK version:\n" + 
                         "• Language: " + applicationVersion.getApplication().getLocale().getLanguage() + "\n" + 
                         "• Package name: \"" + applicationVersion.getApplication().getPackageName() + "\"\n" + 
                         "• Version: " + applicationVersion.getVersionCode() + "\n" +
                         "• Start command: " + applicationVersion.getStartCommand());
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.DEVELOPMENT), text, iconUrl, null);
            }
            
            return "redirect:/admin/application/edit/" + applicationVersion.getApplication().getId();
        }
    }
    
    /**
     * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
     * <p></p>
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'bytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
