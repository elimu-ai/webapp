package ai.elimu.rest.v1.content;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.content.AllophoneGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/content/allophone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AllophoneRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @RequestMapping("/list")
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
