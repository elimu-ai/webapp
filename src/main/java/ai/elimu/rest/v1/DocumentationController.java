package ai.elimu.rest.v1;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/rest/v1/documentation")
public class DocumentationController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
        logger.info("handleRequest");
        
        return "rest/v1/documentation";
    }
}
