package ai.elimu.rest.v2.content;

import ai.elimu.dao.LetterDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.content.LetterGson;
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
@RequestMapping(value = "/rest/v2/content/letters", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class LettersRestController {

  private final LetterDao letterDao;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray lettersJsonArray = new JSONArray();
    for (Letter letter : letterDao.readAllOrdered()) {
      if (letter.getPeerReviewStatus() == PeerReviewStatus.NOT_APPROVED) {
        log.warn("Not approved during peer-review. Skipping.");
        continue;
      }

      LetterGson letterGson = JpaToGsonConverter.getLetterGson(letter);
      String json = new Gson().toJson(letterGson);
      lettersJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = lettersJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
