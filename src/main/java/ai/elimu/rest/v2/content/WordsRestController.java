package ai.elimu.rest.v2.content;

import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.content.WordGson;
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
@RequestMapping(value = "/rest/v2/content/words", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class WordsRestController {

  private final WordDao wordDao;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray wordsJsonArray = new JSONArray();
    for (Word word : wordDao.readAllOrdered()) {
      if (word.getPeerReviewStatus() == PeerReviewStatus.NOT_APPROVED) {
        log.warn("Not approved during peer-review. Skipping.");
        continue;
      }

      WordGson wordGson = JpaToGsonConverter.getWordGson(word);
      String json = new Gson().toJson(wordGson);
      wordsJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = wordsJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
