package org.literacyapp.rest.v1;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The responsibility of this controller is to return the version code of the 
 * newest LiteracyApp APK file uploaded to the website.
 */
@RestController
@RequestMapping("/rest/v1/version")
public class VersionRestController {
    
    public static final Integer NEWEST_VERSION_APPSTORE = 1001000; // 1.1.0 (2016-07-30)
    public static final Integer NEWEST_VERSION_LITERACYAPP = 1001004; // 1.1.4 (2016-06-08)
    public static final Integer NEWEST_VERSION_CHAT = 1000000; // 1.0.0 (2016-07-30)
    
    public static final Integer MINIMUM_OS_VERSION = 21; // Android 5.0
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @RequestMapping("/read")
    public String read(
            @RequestParam Locale locale,
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode,
            @RequestParam String osVersion) {
        logger.info("read");
        
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
