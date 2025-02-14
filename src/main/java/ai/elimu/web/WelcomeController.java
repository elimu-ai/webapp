package ai.elimu.web;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class WelcomeController {

  private final Logger logger = LogManager.getLogger();

  @RequestMapping("/")
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    return "welcome";
  }
}
