package ai.elimu.rest.v2.content;

import ai.elimu.dao.NumberDao;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.content.NumberGson;
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
@RequestMapping(value = "/rest/v2/content/numbers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class NumbersRestController {

  private final NumberDao numberDao;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray numbersJsonArray = new JSONArray();
    for (Number number : numberDao.readAllOrdered()) {
      if (number.getPeerReviewStatus() == PeerReviewStatus.NOT_APPROVED) {
        log.warn("Not approved during peer-review. Skipping.");
        continue;
      }

      NumberGson numberGson = JpaToGsonConverter.getNumberGson(number);
      String json = new Gson().toJson(numberGson);
      numbersJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = numbersJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
