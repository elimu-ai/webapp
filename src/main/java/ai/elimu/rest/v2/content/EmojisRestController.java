package ai.elimu.rest.v2.content;

import ai.elimu.dao.EmojiDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.content.EmojiGson;
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
@RequestMapping(value = "/rest/v2/content/emojis", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class EmojisRestController {

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) {
    log.info("handleGetRequest");

    JSONArray emojisJsonArray = new JSONArray();
    for (Emoji emoji : emojiDao.readAllOrdered()) {
      if (emoji.getPeerReviewStatus() == PeerReviewStatus.NOT_APPROVED) {
        log.warn("Not approved during peer-review. Skipping.");
        continue;
      }

      EmojiGson emojiGson = JpaToGsonConverter.getEmojiGson(emoji);

      String json = new Gson().toJson(emojiGson);
      emojisJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = emojisJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
