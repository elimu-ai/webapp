package ai.elimu.web.content.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-motivation")
@RequiredArgsConstructor
@Slf4j
public class EditMotivationController {

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest() {
    log.info("handleRequest");

    return "content/contributor/edit-motivation";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @RequestParam String motivation,
      Model model) {
    log.info("handleSubmit");

    log.info("motivation: " + motivation);

    if (StringUtils.isBlank(motivation)) {
      model.addAttribute("errorCode", "NotNull.motivation");
      return "content/contributor/edit-motivation";
    } else {
      Contributor contributor = (Contributor) session.getAttribute("contributor");

      if (StringUtils.isBlank(contributor.getMotivation())) {
        // The Contributor completed the on-boarding wizard for the first time

        String contentUrl = DomainHelper.getBaseUrl() + "/contributor/" + contributor.getId();
        String embedThumbnailUrl = null;
        if (StringUtils.isNotBlank(contributor.getImageUrl())) {
          embedThumbnailUrl = contributor.getImageUrl();
        }
        DiscordHelper.sendChannelMessage(
            "Contributor joined: " + contentUrl,
            contributor.getFirstName() + " " + contributor.getLastName(),
            "Motivation: \"" + motivation + "\"",
            null,
            embedThumbnailUrl
        );
      }

      contributor.setMotivation(motivation);
      contributorDao.update(contributor);
      session.setAttribute("contributor", contributor);

      return "redirect:/content";
    }
  }
}
