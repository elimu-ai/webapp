package org.literacyapp.rest.v1.content;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.LetterGson;
import org.literacyapp.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/content/letter")
public class LetterRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray numbers = new JSONArray();
        for (Letter letter : letterDao.readAllOrdered(locale)) {
            LetterGson letterGson = JavaToGsonConverter.getLetterGson(letter);
            String json = new Gson().toJson(letterGson);
            numbers.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("letters", numbers);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
