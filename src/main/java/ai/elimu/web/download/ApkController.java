package ai.elimu.web.download;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import org.apache.logging.log4j.LogManager;

@Controller
@RequestMapping("/apk")
public class ApkController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @GetMapping(value="/{packageName}-{versionCode}.apk")
    public void handleRequest(
            @PathVariable String packageName,
            @PathVariable Integer versionCode,
            
            HttpServletRequest request,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("packageName: " + packageName);
        logger.info("versionCode: " + versionCode);
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        Application application = applicationDao.readByPackageName(packageName);
        logger.info("application: " + application);
        
        ApplicationVersion applicationVersion = applicationVersionDao.read(application, versionCode);
        logger.info("applicationVersion: " + applicationVersion);
        
        response.setContentType(applicationVersion.getContentType());
        
        byte[] bytes = applicationVersion.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
        } catch (EOFException ex) {
            // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
            logger.warn(ex);
        } catch (IOException ex) {
            logger.error(ex);
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
                logger.error(ex);
            }
        }
    }
}
