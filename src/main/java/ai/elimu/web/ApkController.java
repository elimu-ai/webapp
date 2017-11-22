package ai.elimu.web;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.project.License;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/apk")
public class ApkController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @Autowired
    private LicenseDao licenseDao;
    
    @RequestMapping(value="/{packageName}-{versionCode}.apk", method = RequestMethod.GET)
    public void handleRequest(
            @PathVariable String packageName,
            @PathVariable Integer versionCode,
            
            @RequestParam String deviceId,
            @RequestParam String checksum,
            @RequestParam Locale locale,
            @RequestParam String deviceModel,
            @RequestParam Integer osVersion,
            @RequestParam Integer appVersionCode,
            
            // Custom Project
            @RequestParam(required = false) String licenseEmail,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) Long applicationId,
            
            HttpServletRequest request,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("packageName: " + packageName);
        logger.info("versionCode: " + versionCode);
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        // TODO: validate checksum
        
        // See AppCollectionRestController#addInfrastructureApps
        boolean isInfrastructureApp = "ai.elimu.appstore".equals(packageName) 
                || "ai.elimu.analytics".equals(packageName) 
                || "ai.elimu.launcher_custom".equals(packageName);
        
        Application application = null;
        if (TextUtils.isBlank(licenseEmail) || isInfrastructureApp) {
            application = applicationDao.readByPackageName(locale, packageName);
        } else {
            // Custom Project
            License license = licenseDao.read(licenseEmail, licenseNumber);
            if (license != null) {
                // TODO: fetch Application based on License instead of additional applicationId parameter
                application = applicationDao.read(applicationId);
            }
        }
        ApplicationVersion applicationVersion = applicationVersionDao.read(application, versionCode);
        
        response.setContentType(applicationVersion.getContentType());
        
        byte[] bytes = applicationVersion.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
        } catch (EOFException ex) {
            // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
            logger.warn(ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } finally {
            try {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (EOFException ex) {
                    // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
                    logger.warn(ex);
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
    }
}
