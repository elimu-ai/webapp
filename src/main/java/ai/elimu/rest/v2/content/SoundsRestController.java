package ai.elimu.rest.v2.content;

import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.gson.content.SoundGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/sounds", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class SoundsRestController {

  private final SoundDao soundDao;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray soundsJsonArray = new JSONArray();
    for (Sound sound : soundDao.readAllOrdered()) {
      SoundGson soundGson = JpaToGsonConverter.getSoundGson(sound);
      String json = new Gson().toJson(soundGson);
      soundsJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = soundsJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
