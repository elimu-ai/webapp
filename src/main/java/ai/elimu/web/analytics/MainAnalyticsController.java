package ai.elimu.web.analytics;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics")
public class MainAnalyticsController {
    
    private final Logger logger = Logger.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");
        
        // TODO
    	
        return "analytics/main";
    }
}
