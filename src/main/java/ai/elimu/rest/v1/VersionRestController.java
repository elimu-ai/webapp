package ai.elimu.rest.v1;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The responsibility of this controller is to return the latest version code of  
 * the applications that are using the webapp's REST API.
 */
@RestController
@RequestMapping(value = "/rest/v1/version", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VersionRestController {
    
    public static final Integer NEWEST_VERSION_APPSTORE = 1001008; // 1.1.8 (2016-11-12)
    public static final Integer NEWEST_VERSION_CONTENT_PROVIDER = 1001006; // 1.1.6 (2016-08-07)
    
    public static final Integer MINIMUM_OS_VERSION = 21; // Android 5.0
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @RequestMapping("/read")
    public String read(
            HttpServletRequest request,
            @RequestParam Locale locale,
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode,
            @RequestParam String osVersion) {
        logger.info("read");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        Application appStoreApplication = applicationDao.readByPackageName(locale, "ai.elimu.appstore");
        if (appStoreApplication != null) {
            // TODO: fetch dynamically from Application/ApplicationVersion
        }
        
        // TODO: fetch version of ContentProvider
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("newestVersion", NEWEST_VERSION_CONTENT_PROVIDER);
        jsonObject.put("minOsVersion", MINIMUM_OS_VERSION);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
