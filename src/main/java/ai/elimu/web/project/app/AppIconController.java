package ai.elimu.web.project.app;

import ai.elimu.dao.ApplicationVersionDao;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import ai.elimu.model.admin.ApplicationVersion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/project/app-version/{appVersionId}/icon.png")
public class AppIconController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public void handleRequest(
            Model model,
            @PathVariable Long appVersionId,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("appVersionId: " + appVersionId);
        
        ApplicationVersion applicationVersion = applicationVersionDao.read(appVersionId);
        
        response.setContentType("image/png");
        
        byte[] bytes = applicationVersion.getIcon();
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
