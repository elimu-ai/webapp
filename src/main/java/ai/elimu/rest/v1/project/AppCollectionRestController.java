package ai.elimu.rest.v1.project;

import ai.elimu.dao.AppCollectionDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.dao.LicenseDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.gson.admin.ApplicationGson;
import ai.elimu.model.gson.admin.ApplicationVersionGson;
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
    private ApplicationVersionDao applicationVersionDao;
    
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getApplications(
            HttpServletRequest request,
            @PathVariable Long appCollectionId,
            @RequestParam String licenseEmail,
            @RequestParam String licenseNumber) {
        logger.info("read");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        
        License license = licenseDao.read(licenseEmail, licenseNumber);
        if (license == null) {
            jsonObject.put("result", "error");
            jsonObject.put("description", "Invalid license");
        } else {
            JSONArray applications = new JSONArray();
            
            AppCollection appCollection = license.getAppCollection();
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
        
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
