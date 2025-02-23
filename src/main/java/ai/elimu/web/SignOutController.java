package ai.elimu.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sign-out")
@Slf4j
public class SignOutController {

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(HttpServletRequest request) {
    log.debug("handleRequest");

    // Remove Contributor object from session
    request.getSession().removeAttribute("contributor");

    return "redirect:/sign-on?signed_out";
  }
}
