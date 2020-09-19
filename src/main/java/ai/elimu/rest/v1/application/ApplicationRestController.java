package ai.elimu.rest.v1.application;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.rest.v1.service.JsonService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping(value = "/rest/v1/application", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private JsonService jsonService;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            @RequestParam String deviceModel,
            @RequestParam Integer osVersion,
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        JSONArray applications = jsonService.getApplications();
        jsonObject.put("applications", applications);

        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
