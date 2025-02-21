package ai.elimu.rest.v2.content;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.v2.gson.content.NumberGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
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
@RequestMapping(value = "/rest/v2/content/numbers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class NumbersRestController {

  private Logger logger = LogManager.getLogger();

  private final NumberDao numberDao;

  @GetMapping
  public String handleGetRequest() {
    logger.info("handleGetRequest");

    JSONArray numbersJsonArray = new JSONArray();
    for (Number number : numberDao.readAllOrdered()) {
      NumberGson numberGson = JpaToGsonConverter.getNumberGson(number);
      String json = new Gson().toJson(numberGson);
      numbersJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = numbersJsonArray.toString();
    logger.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
