package ai.elimu.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    
    private final Logger logger = Logger.getLogger(getClass());

    @RequestMapping("/")
    public String handleRequest(HttpServletRequest request, Principal principal, Model model) {
    	logger.info("handleRequest");
    	
        return "welcome";
    }
}
