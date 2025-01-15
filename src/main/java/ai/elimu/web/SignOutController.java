package ai.elimu.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sign-out")
public class SignOutController {

    private Logger logger = LogManager.getLogger();

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request) {
        logger.debug("handleRequest");

        // Remove Contributor object from session
        request.getSession().removeAttribute("contributor");
        
        return "redirect:/sign-on?signed_out";
    }
}
