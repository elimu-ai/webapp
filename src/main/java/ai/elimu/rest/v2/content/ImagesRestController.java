package ai.elimu.rest.v2.content;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.gson.content.multimedia.ImageGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import ai.elimu.util.ConfigHelper;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImagesRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpServletRequest request) {
        logger.info("handleGetRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        JSONArray imagesJsonArray = new JSONArray();
        for (Image image : imageDao.readAllOrdered(language)) {
            ImageGson imageGson = JavaToGsonConverter.getImageGson(image);
            
            String json = new Gson().toJson(imageGson);
            imagesJsonArray.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("images", imagesJsonArray);
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
