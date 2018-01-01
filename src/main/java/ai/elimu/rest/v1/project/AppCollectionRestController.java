package ai.elimu.rest.v1.project;

import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.admin.ApplicationGson;
import ai.elimu.model.gson.admin.ApplicationVersionGson;
import ai.elimu.model.gson.project.AppCollectionGson;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.AppGroup;
import ai.elimu.model.project.License;
import ai.elimu.rest.v1.JavaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
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
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
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
                
                JSONArray applications = new JSONArray();
                
                addInfrastructureApps(applications);
                
                for (AppCategory appCategory : appCollection.getAppCategories()) {
                    for (AppGroup appGroup : appCategory.getAppGroups()) {
                        for (Application application : appGroup.getApplications()) {
                            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(application);

                            // Fetch the Application's ApplicationVersions
                            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
                            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(application)) {
                                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                                applicationVersions.add(applicationVersionGson);
                            }
                            applicationGson.setApplicationVersions(applicationVersions);

                            String json = new Gson().toJson(applicationGson);
                            applications.put(new JSONObject(json));
                        }
                    }
                }

                jsonObject.put("result", "success");
                jsonObject.put("applications", applications);
            }
        }
        
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
    
    /**
     * As AppCollections in Custom Projects do not include all the Applications from the open source 
     * version, some apps are required to form the basic infrastructure.
     */
    private void addInfrastructureApps(JSONArray applications) {
        // Appstore
        Application applicationAppstore = applicationDao.readByPackageName(Locale.EN, "ai.elimu.appstore");
        if (applicationAppstore != null) {
            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(applicationAppstore);

            // Fetch the Application's ApplicationVersions
            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(applicationAppstore)) {
                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersions.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersions);

            String json = new Gson().toJson(applicationGson);
            applications.put(new JSONObject(json));
        }
        
        // Analytics
        Application applicationAnalytics = applicationDao.readByPackageName(Locale.EN, "ai.elimu.analytics");
        if (applicationAnalytics != null) {
            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(applicationAnalytics);

            // Fetch the Application's ApplicationVersions
            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(applicationAnalytics)) {
                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersions.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersions);

            String json = new Gson().toJson(applicationGson);
            applications.put(new JSONObject(json));
        }
        
        // Custom Launcher
        Application applicationCustomLauncher = applicationDao.readByPackageName(Locale.EN, "ai.elimu.launcher_custom");
        if (applicationCustomLauncher != null) {
            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(applicationCustomLauncher);

            // Fetch the Application's ApplicationVersions
            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(applicationCustomLauncher)) {
                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersions.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersions);

            String json = new Gson().toJson(applicationGson);
            applications.put(new JSONObject(json));
        }
    }
}
