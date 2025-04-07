package ai.elimu.web;

import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/sign-on/test/role-{role}")
@RequiredArgsConstructor
@Slf4j
public class SignOnControllerSelenium {

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest(
      @PathVariable Role role,
      HttpServletRequest request,
      Model model
  ) {
    log.info("handleRequest");

    if (EnvironmentContextLoaderListener.env == Environment.PROD) {
      return "redirect:/sign-on";
    }

    log.info("role: " + role);

    Contributor contributor = new Contributor();
    contributor.setEmail("info+role-" + role + "@elimu.ai");
    contributor.setRoles(new HashSet<>(Arrays.asList(role)));
    contributor.setRegistrationTime(Calendar.getInstance());
    contributor.setFirstName("TestRole");
    contributor.setLastName(role.toString());
    contributor.setMotivation("Regression testing as " + role);

    Contributor existingContributor = contributorDao.read(contributor.getEmail());
    log.info("existingContributor: " + existingContributor);
    if (existingContributor != null) {
      contributor = existingContributor;
    } else {
      contributorDao.create(contributor);
      log.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
    }

    // Add Contributor object to session
    request.getSession().setAttribute("contributor", contributor);

    return "redirect:/content";
  }
}
