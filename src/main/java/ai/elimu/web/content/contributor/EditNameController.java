package ai.elimu.web.content.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-name")
@RequiredArgsConstructor
public class EditNameController {

  private final Logger logger = LogManager.getLogger();

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest() {
    logger.info("handleRequest");

    return "content/contributor/edit-name";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @RequestParam String firstName,
      @RequestParam String lastName,
      Model model) {
    logger.info("handleSubmit");

    logger.info("firstName: " + firstName);
    logger.info("lastName: " + lastName);
    // TODO: validate firstName/lastName

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    contributor.setFirstName(firstName);
    contributor.setLastName(lastName);
    contributorDao.update(contributor);
    session.setAttribute("contributor", contributor);

    return "redirect:/content";
  }
}
