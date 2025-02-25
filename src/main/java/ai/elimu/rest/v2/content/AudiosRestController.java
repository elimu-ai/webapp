package ai.elimu.rest.v2.content;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.v2.gson.content.AudioGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/audios", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AudiosRestController {

  private final AudioDao audioDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleGetRequest(HttpServletRequest request) {
    log.info("handleGetRequest");

    JSONArray audiosJsonArray = new JSONArray();
    for (Audio audio : audioDao.readAllOrderedByTitle()) {
      AudioGson audioGson = JpaToGsonConverter.getAudioGson(audio);

      String json = new Gson().toJson(audioGson);
      audiosJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = audiosJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
