package ai.elimu.rest.v1.project;

import ai.elimu.dao.LicenseDao;
import ai.elimu.model.project.License;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Checks if a license number is valid.
 */
@RestController
@RequestMapping(value = "/rest/v1/project/licenses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LicenseRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LicenseDao licenseDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String read(
            HttpServletRequest request,
            @RequestParam String licenseEmail,
            @RequestParam String licenseNumber) {
        logger.info("read");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jsonObject = new JSONObject();
        
        License license = licenseDao.read(licenseEmail, licenseNumber);
        if (license != null) {
            jsonObject.put("result", "success");
            jsonObject.put("appCollectionId", license.getAppCollection().getId());
        } else {
            jsonObject.put("result", "error");
            jsonObject.put("description", "Invalid license");
        }
        
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
