package ai.elimu.web;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-on")
@RequiredArgsConstructor
public class SignOnController {

  private Logger logger = LogManager.getLogger();

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.debug("handleRequest");

    return "sign-on";
  }

  /**
   * To make it possible to sign on without an active Internet connection, enable sign-on with a test user during offline development.
   */
  @RequestMapping("/offline")
  public String handleOfflineSignOnRequest(HttpServletRequest request) {
    logger.info("handleOfflineSignOnRequest");

    if (EnvironmentContextLoaderListener.env == Environment.DEV) {
      // Fetch the test user that was created in DbContentImportHelper during application launch
      Contributor contributor = contributorDao.read("dev@elimu.ai");

      // Add Contributor object to session
      request.getSession().setAttribute("contributor", contributor);

      return "redirect:/content";
    } else {
      return null;
    }
  }
}
