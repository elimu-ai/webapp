package ai.elimu.rest.v2.content;

import ai.elimu.rest.v2.service.StoryBooksJsonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/storybooks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class StoryBooksRestController {

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleGetRequest() {
    log.info("handleGetRequest");

    JSONArray storyBooksJsonArray = storyBooksJsonService.getStoryBooksJSONArray();
    String jsonResponse = storyBooksJsonArray.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
