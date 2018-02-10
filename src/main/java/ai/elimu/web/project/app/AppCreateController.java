package ai.elimu.web.project.app;

import ai.elimu.dao.AppCategoryDao;
import ai.elimu.dao.AppGroupDao;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppGroup;
import ai.elimu.model.project.Project;
import ai.elimu.service.JsonService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/project/{projectId}/app-category/{appCategoryId}/app-group/{appGroupId}/app/create")
public class AppCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private JsonService jsonService;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private AppCategoryDao appCategoryDao;
    
    @Autowired
    private AppGroupDao appGroupDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long projectId,
            @PathVariable Long appCategoryId,
            @PathVariable Long appGroupId,
            @RequestParam(required = false) Long applicationId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        Project project = projectDao.read(projectId);
        model.addAttribute("project", project);
        
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        model.addAttribute("appCategory", appCategory);
        
        AppGroup appGroup = appGroupDao.read(appGroupId);
        model.addAttribute("appGroup", appGroup);
        
        ApplicationVersion applicationVersion = new ApplicationVersion();
        
        logger.info("applicationId: " + applicationId);
        if (applicationId != null) {
            Application application = applicationDao.read(applicationId);
            applicationVersion.setApplication(application);
        }
        
        model.addAttribute("applicationVersion", applicationVersion);

        return "project/app/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            ApplicationVersion applicationVersion,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model,
            HttpSession session,
            @PathVariable Long appGroupId,
            @PathVariable Long projectId,
            @PathVariable Long appCategoryId
    ) {
    	logger.info("handleSubmit");
        
        Project project = projectDao.read(projectId);
        AppCategory appCategory = appCategoryDao.read(appCategoryId);
        AppGroup appGroup = appGroupDao.read(appGroupId);
        
        boolean isUpdateOfExistingApplication = applicationVersion.getApplication() != null;
        logger.info("isUpdateOfExistingApplication: " + isUpdateOfExistingApplication);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        String packageName = null;
        
        if (multipartFile.isEmpty()) {
            result.rejectValue("bytes", "NotNull");
        } else {
            try {
                byte[] bytes = multipartFile.getBytes();
                
                Integer fileSizeInKb = bytes.length / 1024;
                logger.info("fileSizeInKb: " + fileSizeInKb + " (" + (fileSizeInKb / 1024) + "MB)");
                
                String contentType = multipartFile.getContentType();
                logger.info("contentType: " + contentType);
                
                // Auto-detect applicationId, versionCode, etc.
                ByteArrayApkFile byteArrayApkFile = new ByteArrayApkFile(bytes);
                ApkMeta apkMeta = byteArrayApkFile.getApkMeta();
                
                packageName = apkMeta.getPackageName();
                logger.info("packageName: " + packageName);
                if (isUpdateOfExistingApplication) {
                    // Verify that the packageName of the new APK matches that of the Application
                    // TODO
                }
                
                if (!isUpdateOfExistingApplication) {
                    // Verify that an Application with identical packageName has not already been uploaded withing the same Project
                    Application existingApplication = applicationDao.readByPackageName(project, packageName);
                    if (existingApplication != null) {
                        result.rejectValue("application", "NonUnique", new String[] {"application"}, null);
                    }
                }
                
                String label = apkMeta.getLabel();
                logger.info("label: " + label);
                
                byte[] icon = byteArrayApkFile.getIconFile().getData();
                logger.info("icon.length: " + (icon.length / 1024) + "kB");
                
                Integer versionCode = apkMeta.getVersionCode().intValue();
                logger.info("versionCode: " + versionCode);
                if (isUpdateOfExistingApplication) {
                    // Verify that the versionCode of the new APK is higher than the previous one
                    // TODO
                }
                
                String versionName = apkMeta.getVersionName();
                logger.info("versionName: " + versionName);
                
                String minSdkVersion = apkMeta.getMinSdkVersion();
                logger.info("minSdkVersion: " + minSdkVersion);
                
                // Check if Application already exists in the same AppCategory
                // TODO
                
                applicationVersion.setBytes(bytes);
                applicationVersion.setFileSizeInKb(fileSizeInKb);
                applicationVersion.setChecksumMd5(ChecksumHelper.calculateMD5(bytes));
                applicationVersion.setContentType(contentType);
                applicationVersion.setVersionCode(versionCode);
                applicationVersion.setVersionName(versionName);
                applicationVersion.setLabel(label);
                applicationVersion.setIcon(icon);
                // TODO: set minSdkVersion
                applicationVersion.setTimeUploaded(Calendar.getInstance());
                applicationVersion.setContributor(contributor);
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("appCategory", appCategory);
            model.addAttribute("appGroup", appGroup);
            return "project/app/create";
        } else {
            Application application = applicationVersion.getApplication();
            logger.info("application: " + application);
            if (application == null) {
                // First-time upload for packageName
                
                // Create new Application
                application = new Application();
                application.setLocale(contributor.getLocale()); // TODO: Add Locale to each Project?
                application.setPackageName(packageName);
                application.setApplicationStatus(ApplicationStatus.ACTIVE);
                application.setContributor(contributor);
                application.setProject(project);
                applicationDao.create(application);

                applicationVersion.setApplication(application);
                applicationVersionDao.create(applicationVersion);

                appGroup.getApplications().add(application);
                appGroupDao.update(appGroup);
            } else {
                // Update of existing packageName previously uploaded
                
                // Create new ApplicationVersion for the existing Application
                applicationVersionDao.create(applicationVersion);
            }
            
            // Refresh REST API cache
//            jsonService.refreshApplicationsInAppCollection(appCollection);
            jsonService.refreshApplicationsInAppCollection();
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String applicationDescription = !isUpdateOfExistingApplication ? "Application" : "APK version";
                String text = URLEncoder.encode(
                         contributor.getFirstName() + " just uploaded a new " + applicationDescription + ":\n" + 
                         "• Project: \"" + project.getName() + "\"\n" +
                         "• App Category: \"" + appCategory.getName() + "\"\n" +
                         "• Package name: \"" + application.getPackageName() + "\"\n" + 
                         "• Version code: " + applicationVersion.getVersionCode() + "\n" +
                        "See ") + "http://elimu.ai/project/" + project.getId() + "/app-category/" + appCategory.getId() + "/app-group/" + appGroup.getId() + "/app/" + application.getId() + "/edit";
                SlackApiHelper.postMessage("G6UR7UH2S", text, null, null);
            }
            
            if (!isUpdateOfExistingApplication) {
                return "redirect:/project/{projectId}/app-category/{appCategoryId}/app-group/{appGroupId}/app/list#" + application.getId();
            } else {
                return "redirect:/project/{projectId}/app-category/{appCategoryId}/app-group/{appGroupId}/app/" + application.getId() + "/edit";
            }
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
