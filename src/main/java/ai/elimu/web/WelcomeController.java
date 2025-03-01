package ai.elimu.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WelcomeController {

  @RequestMapping("/")
  public String handleRequest(Model model) {
    log.info("handleRequest");

    return "welcome";
  }
}
