package org.literacyapp.rest.v1.content;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.AllophoneGson;
import org.literacyapp.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/content/allophone")
public class AllophoneRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray allophones = new JSONArray();
        for (Allophone allophone : allophoneDao.readAllOrdered(locale)) {
            AllophoneGson allophoneGson = JavaToGsonConverter.getAllophoneGson(allophone);
            String json = new Gson().toJson(allophoneGson);
            allophones.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("allophones", allophones);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
