package ai.elimu.rest.v2.content;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.model.v2.gson.content.LetterSoundGson;
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
@RequestMapping(value = "/rest/v2/content/letter-sounds", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class LetterSoundsRestController {

  private final LetterSoundDao letterSoundDao;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray letterSoundsJsonArray = new JSONArray();
    for (LetterSound letterSound : letterSoundDao.readAllOrderedByUsage()) {
      LetterSoundGson letterSoundGson = JpaToGsonConverter.getLetterSoundGson(letterSound);
      String json = new Gson().toJson(letterSoundGson);
      letterSoundsJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = letterSoundsJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
