package ai.elimu.rest.v2.content;

import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.v2.gson.content.LetterGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
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
@RequestMapping(value = "/rest/v2/content/letters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class LettersRestController {

  private Logger logger = LogManager.getLogger();

  private final LetterDao letterDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleGetRequest() {
    logger.info("handleGetRequest");

    JSONArray lettersJsonArray = new JSONArray();
    for (Letter letter : letterDao.readAllOrdered()) {
      LetterGson letterGson = JpaToGsonConverter.getLetterGson(letter);
      String json = new Gson().toJson(letterGson);
      lettersJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = lettersJsonArray.toString();
    logger.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
