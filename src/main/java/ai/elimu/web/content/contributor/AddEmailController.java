package ai.elimu.web.content.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller handles users redirected from SignOnControllerGitHub because of missing e-mail.
 */
@Controller
@RequestMapping("/content/contributor/add-email")
@RequiredArgsConstructor
@Slf4j
public class AddEmailController {

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest() {
    log.info("handleRequest");

    return "content/contributor/add-email";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @RequestParam String email,
      Model model) {
    log.info("handleSubmit");

    if (!EmailValidator.getInstance().isValid(email)) {
      // TODO: display error message
      return "content/contributor/add-email";
    }

    // Look for existing Contributor with matching e-mail address
    Contributor existingContributor = contributorDao.read(email);
    if (existingContributor != null) {
      // TODO: display error message
      return "content/contributor/add-email";
    }

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    contributor.setEmail(email);
    contributorDao.create(contributor);

    session.setAttribute("contributor", contributor);

    return "redirect:/content";
  }
}
