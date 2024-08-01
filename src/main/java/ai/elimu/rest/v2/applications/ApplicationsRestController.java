package ai.elimu.rest.v2.applications;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.v2.gson.application.ApplicationGson;
import ai.elimu.model.v2.gson.application.ApplicationVersionGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/applications", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpServletRequest request) {
        logger.info("handleGetRequest");
        
        JSONArray applicationsJsonArray = new JSONArray();
        for (Application application : applicationDao.readAll()) {
            ApplicationGson applicationGson = JpaToGsonConverter.getApplicationGson(application);
            
            List<ApplicationVersionGson> applicationVersionGsons = new ArrayList<>();
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(application)) {
                ApplicationVersionGson applicationVersionGson = JpaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersionGsons.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersionGsons);
            
            String json = new Gson().toJson(applicationGson);
            applicationsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = applicationsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
