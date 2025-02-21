package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/edit")
@RequiredArgsConstructor
public class LetterEditController {

  private final Logger logger = LogManager.getLogger();
    
  private final LetterDao letterDao;

  private final LetterContributionEventDao letterContributionEventDao;

  private final LetterSoundDao letterSoundDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(
      Model model,
      @PathVariable Long id) {
    logger.info("handleRequest");

    Letter letter = letterDao.read(id);
    model.addAttribute("letter", letter);
    model.addAttribute("timeStart", System.currentTimeMillis());

    model.addAttribute("letterContributionEvents", letterContributionEventDao.readAll(letter));
    
    model.addAttribute("letterSounds", letterSoundDao.readAll());

    return "content/letter/edit";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Letter letter,
      BindingResult result,
      Model model) {
    logger.info("handleSubmit");

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
