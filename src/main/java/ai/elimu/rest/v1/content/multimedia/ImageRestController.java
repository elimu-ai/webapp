package ai.elimu.rest.v1.content.multimedia;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.content.multimedia.ImageGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/content/multimedia/image", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ImageRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray jsonArray = new JSONArray();
        for (Image image : imageDao.readAllOrdered(locale)) {
            ImageGson imageGson = JavaToGsonConverter.getImageGson(image);
            String json = new Gson().toJson(imageGson);
            jsonArray.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("images", jsonArray);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
