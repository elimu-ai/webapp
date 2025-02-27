package ai.elimu.web.content.letter_sound;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Sound;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/letter-sound/create")
@RequiredArgsConstructor
public class LetterSoundCreateController {

  private final Logger logger = LogManager.getLogger();

  private final LetterSoundDao letterSoundDao;

  private final LetterSoundContributionEventDao letterSoundContributionEventDao;

  private final LetterDao letterDao;

  private final SoundDao soundDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    LetterSound letterSound = new LetterSound();
    model.addAttribute("letterSound", letterSound);

    List<Letter> letters = letterDao.readAllOrdered();
    model.addAttribute("letters", letters);

    List<Sound> sounds = soundDao.readAllOrdered();
    model.addAttribute("sounds", sounds);

    model.addAttribute("timeStart", System.currentTimeMillis());

    return "content/letter-sound/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid LetterSound letterSound,
      BindingResult result,
      Model model
  ) {
    logger.info("handleSubmit");

    // Check if the LetterSound already exists
    LetterSound existingLetterSound = letterSoundDao.read(letterSound.getLetters(), letterSound.getSounds());
    if (existingLetterSound != null) {
      result.rejectValue("letters", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("letterSound", letterSound);

      List<Letter> letters = letterDao.readAllOrdered();
      model.addAttribute("letters", letters);

      List<Sound> sounds = soundDao.readAllOrdered();
      model.addAttribute("sounds", sounds);

      model.addAttribute("timeStart", System.currentTimeMillis());

      return "content/letter-sound/create";
    } else {
      letterSound.setTimeLastUpdate(Calendar.getInstance());
      letterSoundDao.create(letterSound);

      LetterSoundContributionEvent letterSoundContributionEvent = new LetterSoundContributionEvent();
      letterSoundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      letterSoundContributionEvent.setTimestamp(Calendar.getInstance());
      letterSoundContributionEvent.setLetterSound(letterSound);
      letterSoundContributionEvent.setRevisionNumber(letterSound.getRevisionNumber());
      letterSoundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      letterSoundContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      letterSoundContributionEventDao.create(letterSoundContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound/edit/" + letterSound.getId();
        DiscordHelper.sendChannelMessage(
            "Letter-sound correspondence created: " + contentUrl,
            "\"" + letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
            "Comment: \"" + letterSoundContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      return "redirect:/content/letter-sound/list#" + letterSound.getId();
    }
  }
}
