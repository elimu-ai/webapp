package ai.elimu.rest.v1.application;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.model.enums.Locale;
import ai.elimu.rest.v1.ChecksumHelper;
import ai.elimu.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/application", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private JsonService jsonService;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            @RequestParam String checksum,
            @RequestParam Locale locale,
            @RequestParam String deviceModel,
            @RequestParam Integer osVersion,
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        
        if (!checksum.equals(ChecksumHelper.getChecksum(deviceId))) {
            jsonObject.put("result", "error");
            jsonObject.put("description", "Incorrect checksum: " + checksum);
            return jsonObject.toString();
        } else {        
            JSONArray applications = jsonService.getApplications(locale);

            jsonObject.put("result", "success");
            jsonObject.put("applications", applications);

            logger.info("jsonObject: " + jsonObject);
            return jsonObject.toString();
        }
    }
}
