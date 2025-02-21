package ai.elimu.rest.v2.content;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.v2.gson.content.AudioGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/audios", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AudiosRestController {

  private Logger logger = LogManager.getLogger();

  private final AudioDao audioDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) {
    logger.info("handleGetRequest");

    JSONArray audiosJsonArray = new JSONArray();
    for (Audio audio : audioDao.readAllOrderedByTitle()) {
      AudioGson audioGson = JpaToGsonConverter.getAudioGson(audio);

      String json = new Gson().toJson(audioGson);
      audiosJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = audiosJsonArray.toString();
    logger.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
