package ai.elimu.rest.v2.content;

import ai.elimu.rest.v2.service.StoryBooksJsonService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/storybooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class StoryBooksRestController {

  private Logger logger = LogManager.getLogger();

  private final StoryBooksJsonService storyBooksJsonService;

  @RequestMapping(method = RequestMethod.GET)
  public String handleGetRequest() {
    logger.info("handleGetRequest");

    JSONArray storyBooksJsonArray = storyBooksJsonService.getStoryBooksJSONArray();
    String jsonResponse = storyBooksJsonArray.toString();
    logger.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
