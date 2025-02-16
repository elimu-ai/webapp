package ai.elimu.web;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-out")
public class SignOutController {

  private Logger logger = LogManager.getLogger();

  @GetMapping
  public String handleRequest(HttpServletRequest request) {
    logger.debug("handleRequest");

    // Remove Contributor object from session
    request.getSession().removeAttribute("contributor");

    return "redirect:/sign-on?signed_out";
  }
}
