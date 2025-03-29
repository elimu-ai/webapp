package ai.elimu.web.content.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-name")
@RequiredArgsConstructor
@Slf4j
public class EditNameController {

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest() {
    log.info("handleRequest");

    return "content/contributor/edit-name";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @RequestParam String firstName,
      @RequestParam String lastName,
      Model model) {
    log.info("handleSubmit");

    log.info("firstName: " + firstName);
    log.info("lastName: " + lastName);
    // TODO: validate firstName/lastName

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    contributor.setFirstName(firstName);
    contributor.setLastName(lastName);
    contributorDao.update(contributor);
    session.setAttribute("contributor", contributor);

    return "redirect:/content";
  }
}
