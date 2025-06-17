package ai.elimu.rest.v2.content;

import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.content.VideoGson;
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
@RequestMapping(value = "/rest/v2/content/videos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class VideosRestController {

  private final VideoDao videoDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) {
    log.info("handleGetRequest");

    JSONArray videosJsonArray = new JSONArray();
    for (Video video : videoDao.readAllOrdered()) {
      if (video.getPeerReviewStatus() == PeerReviewStatus.NOT_APPROVED) {
        log.warn("Not approved during peer-review. Skipping.");
        continue;
      }

      VideoGson videoGson = JpaToGsonConverter.getVideoGson(video);

      String json = new Gson().toJson(videoGson);
      videosJsonArray.put(new JSONObject(json));
    }

    String jsonResponse = videosJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
