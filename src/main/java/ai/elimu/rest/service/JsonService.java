package ai.elimu.rest.service;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.admin.ApplicationGson;
import ai.elimu.model.gson.admin.ApplicationVersionGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Used as layer between Controllers and DAOs in order to enable usage of caching.
 * <p />
 * Spring caching feature works over AOP proxies, thus internal calls to cached methods don't work. That's why this 
 * intermediate service is used. See https://stackoverflow.com/a/48168762
 */
@Service
public class JsonService {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @Cacheable("applications")
    public JSONArray getApplications(Locale locale) {
        logger.info("getApplications");
        
        Date dateStart = new Date();
        
        JSONArray applications = new JSONArray();
        for (Application application : applicationDao.readAll(locale)) {
            ApplicationGson applicationGson = JavaToGsonConverter.getApplicationGson(application);

            List<ApplicationVersionGson> applicationVersions = new ArrayList<>();
            logger.info("applicationVersionDao.readAll(" + application.getPackageName() + ") - " + new Date());
            for (ApplicationVersion applicationVersion : applicationVersionDao.readAll(application)) {
                logger.info("applicationVersion: " + applicationVersion.getVersionCode() + " - " + new Date());
                ApplicationVersionGson applicationVersionGson = JavaToGsonConverter.getApplicationVersionGson(applicationVersion);
                applicationVersions.add(applicationVersionGson);
            }
            applicationGson.setApplicationVersions(applicationVersions);
            String json = new Gson().toJson(applicationGson);
            applications.put(new JSONObject(json));
        }
        
        Date dateEnd = new Date();
        logger.info("getApplications duration: " + (dateEnd.getTime() - dateStart.getTime()) + " ms");
        
        return applications;
    }
    
    @CacheEvict("applications")
    public void refreshApplications(Locale locale) {
        logger.info("refreshApplications");
    }
}
