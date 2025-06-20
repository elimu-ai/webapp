package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/letter/create")
@RequiredArgsConstructor
@Slf4j
public class LetterCreateController {

  private final LetterDao letterDao;

  private final LetterContributionEventDao letterContributionEventDao;

  @GetMapping
  public String handleRequest(
      Model model) {
    log.info("handleRequest");

    Letter letter = new Letter();
    model.addAttribute("letter", letter);

    return "content/letter/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Letter letter,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    Letter existingLetter = letterDao.readByText(letter.getText());
    if (existingLetter != null) {
      result.rejectValue("text", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("letter", letter);

      return "content/letter/create";
    } else {
      letterDao.create(letter);

      LetterContributionEvent letterContributionEvent = new LetterContributionEvent();
      letterContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      letterContributionEvent.setTimestamp(Calendar.getInstance());
      letterContributionEvent.setLetter(letter);
      letterContributionEvent.setRevisionNumber(letter.getRevisionNumber());
      letterContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      letterContributionEventDao.create(letterContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/letter/edit/" + letter.getId();
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Letter created: " + contentUrl,
          "\"" + letterContributionEvent.getLetter().getText() + "\"",
          "Comment: \"" + letterContributionEvent.getComment() + "\"",
          null,
          null
      );

      return "redirect:/content/letter/list#" + letter.getId();
    }
  }
}
