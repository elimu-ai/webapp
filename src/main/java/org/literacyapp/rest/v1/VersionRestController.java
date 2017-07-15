package org.literacyapp.rest.v1;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The responsibility of this controller is to return the version code of the 
 * newest elimu.ai APK file uploaded to the website.
 */
@RestController
@RequestMapping(value = "/rest/v1/version", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VersionRestController {
    
    public static final Integer NEWEST_VERSION_APPSTORE = 1001008; // 1.1.8 (2016-11-12)
    public static final Integer NEWEST_VERSION_LITERACYAPP = 1001006; // 1.1.6 (2016-08-07)
    public static final Integer NEWEST_VERSION_CHAT = 1001002; // 1.1.2 (2016-08-08)
    
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
        
        Application application = applicationDao.readByPackageName(locale, "org.literacyapp");
        if (application != null) {
            // TODO: fetch dynamically from Application/ApplicationVersion
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("newestVersion", NEWEST_VERSION_LITERACYAPP);
        jsonObject.put("minOsVersion", MINIMUM_OS_VERSION);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
