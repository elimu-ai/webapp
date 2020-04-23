package ai.elimu.web;

import ai.elimu.model.enums.Language;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublishController {
    
    private final Logger logger = Logger.getLogger(getClass());

    @RequestMapping("/publish")
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language[] supportedLanguages = Language.values();
        model.addAttribute("supportedLanguages", supportedLanguages);
    	
        return "publish";
    }
}
