package ai.elimu.rest.v1.project;

import ai.elimu.dao.project.AppCollectionDao;
import ai.elimu.dao.project.LicenseDao;
import ai.elimu.model.gson.project.AppCollectionGson;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.License;
import ai.elimu.rest.v1.JavaToGsonConverter;
import ai.elimu.rest.service.project.ProjectJsonService;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Returns the {@link AppCollection} for a given {@link License}.
 * <p />
 * See https://github.com/elimu-ai/webapp/blob/master/REST_API_REFERENCE.md#appcollection
 */
@RestController
@RequestMapping(value = "/rest/v1/project/app-collections/{appCollectionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppCollectionRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LicenseDao licenseDao;
    
    @Autowired
    private AppCollectionDao appCollectionDao;
    
    @Autowired
    private ProjectJsonService projectJsonService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(
            HttpServletRequest request,
            @PathVariable Long appCollectionId,
            @RequestParam String licenseEmail,
            @RequestParam String licenseNumber) {
        logger.info("get");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        
        License license = licenseDao.read(licenseEmail, licenseNumber);
        if (license == null) {
            jsonObject.put("result", "error");
            jsonObject.put("description", "Invalid license");
        } else {
            AppCollection appCollection = appCollectionDao.read(appCollectionId);
            if (appCollection == null) {
                jsonObject.put("result", "error");
                jsonObject.put("description", "AppCollection not found");
            } else {
                // TODO: verify that the AppCollection matches the one associated with the provided License
                
                AppCollectionGson appCollectionGson = JavaToGsonConverter.getAppCollectionGson(appCollection);
                String appCollectionJson = new Gson().toJson(appCollectionGson);
                
                jsonObject.put("result", "success");
                jsonObject.put("appCollection", new JSONObject(appCollectionJson));
            }
        }
        
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
    
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getApplications(
            HttpServletRequest request,
            @PathVariable Long appCollectionId,
            @RequestParam String licenseEmail,
            @RequestParam String licenseNumber) {
        logger.info("getApplications");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        
        License license = licenseDao.read(licenseEmail, licenseNumber);
        if (license == null) {
            jsonObject.put("result", "error");
            jsonObject.put("description", "Invalid license");
        } else {
            AppCollection appCollection = appCollectionDao.read(appCollectionId);
            if (appCollection == null) {
                jsonObject.put("result", "error");
                jsonObject.put("description", "AppCollection not found");
            } else {
                // TODO: verify that the AppCollection matches the one associated with the provided License
                
                JSONArray applications = projectJsonService.getApplications(appCollection);

                jsonObject.put("result", "success");
                jsonObject.put("applications", applications);
            }
        }
        
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
