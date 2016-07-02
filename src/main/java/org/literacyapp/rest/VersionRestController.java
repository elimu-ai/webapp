package org.literacyapp.rest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/version")
public class VersionRestController {
    
    private static final Integer NEWEST_VERSION_ANDROID = 1001004; // 1.1.4 (2016-06-08)
    
    private Logger logger = Logger.getLogger(getClass());
    
    @RequestMapping("/read")
    public String read() {
        logger.info("read");
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newestVersion", NEWEST_VERSION_ANDROID);
        return jsonObject.toString();
    }
}
