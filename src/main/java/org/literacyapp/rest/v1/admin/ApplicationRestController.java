package org.literacyapp.rest.v1.admin;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.dao.ApplicationVersionDao;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.admin.ApplicationVersion;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.admin.ApplicationStatus;
import org.literacyapp.model.gson.admin.ApplicationGson;
import org.literacyapp.model.gson.admin.ApplicationVersionGson;
import org.literacyapp.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/admin/application")
public class ApplicationRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum
            @RequestParam Locale locale,
            @RequestParam String deviceModel,
            @RequestParam Integer osVersion,
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONArray applications = new JSONArray();
        for (Application application : applicationDao.readAllByStatus(locale, ApplicationStatus.ACTIVE)) {
            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(application);
            
            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(application)) {
                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersions.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersions);
            String json = new Gson().toJson(applicationGson);
            applications.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("applications", applications);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
