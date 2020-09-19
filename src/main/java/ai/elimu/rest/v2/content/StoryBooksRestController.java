package ai.elimu.rest.v2.content;

import ai.elimu.rest.v2.service.StoryBooksJsonService;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/storybooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoryBooksRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray storyBooksJsonArray = storyBooksJsonService.getStoryBooksJSONArray();        
        String jsonResponse = storyBooksJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
