package ai.elimu.rest.v2.content;

import ai.elimu.dao.ImageDao;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class ImagesRestController {

  private final ImageDao imageDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) {
    log.info("handleGetRequest");

    JSONArray imagesJsonArray = new JSONArray();
    for (Image image : imageDao.readAllOrdered()) {
      ImageGson imageGson = JpaToGsonConverter.getImageGson(image);

      String json = new Gson().toJson(imageGson);
      imagesJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = imagesJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
