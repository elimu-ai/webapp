package ai.elimu.rest.v1;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/rest/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FallbackRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @RequestMapping
    public String handleRequest() {
        logger.info("handleRequest");
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "URL not found. See https://github.com/elimu-ai/webapp/tree/master/src/main/java/ai/elimu/rest");
        
        return jsonObject.toString();
    }
}
