package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/letter/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class LetterEditController {

  private final LetterDao letterDao;

  private final LetterContributionEventDao letterContributionEventDao;

  private final LetterSoundDao letterSoundDao;

  @GetMapping
  public String handleRequest(
      Model model,
      @PathVariable Long id) {
    log.info("handleRequest");

    Letter letter = letterDao.read(id);
    model.addAttribute("letter", letter);
    model.addAttribute("timeStart", System.currentTimeMillis());

    model.addAttribute("letterContributionEvents", letterContributionEventDao.readAll(letter));
    
    model.addAttribute("letterSounds", letterSoundDao.readAll());

    return "content/letter/edit";
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
    if ((existingLetter != null) && !existingLetter.getId().equals(letter.getId())) {
      result.rejectValue("text", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("letter", letter);
      model.addAttribute("timeStart", System.currentTimeMillis());

      model.addAttribute("letterContributionEvents", letterContributionEventDao.readAll(letter));

      model.addAttribute("letterSounds", letterSoundDao.readAll());

      return "content/letter/edit";
    } else {
      letter.setTimeLastUpdate(Calendar.getInstance());
      letter.setRevisionNumber(letter.getRevisionNumber() + 1);
      letterDao.update(letter);

      LetterContributionEvent letterContributionEvent = new LetterContributionEvent();
      letterContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      letterContributionEvent.setTimestamp(Calendar.getInstance());
      letterContributionEvent.setLetter(letter);
      letterContributionEvent.setRevisionNumber(letter.getRevisionNumber());
      letterContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      letterContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      letterContributionEventDao.create(letterContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter/edit/" + letter.getId();
        DiscordHelper.sendChannelMessage(
            "Letter edited: " + contentUrl,
            "\"" + letterContributionEvent.getLetter().getText() + "\"",
            "Comment: \"" + letterContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      return "redirect:/content/letter/list#" + letter.getId();
    }
  }
}
