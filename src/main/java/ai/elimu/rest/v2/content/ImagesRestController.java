package ai.elimu.rest.v2.content;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ImagesRestController {

  private Logger logger = LogManager.getLogger();

  private final ImageDao imageDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleGetRequest(HttpServletRequest request) {
    logger.info("handleGetRequest");

    JSONArray imagesJsonArray = new JSONArray();
    for (Image image : imageDao.readAllOrdered()) {
      ImageGson imageGson = JpaToGsonConverter.getImageGson(image);

      String json = new Gson().toJson(imageGson);
      imagesJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = imagesJsonArray.toString();
    logger.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
