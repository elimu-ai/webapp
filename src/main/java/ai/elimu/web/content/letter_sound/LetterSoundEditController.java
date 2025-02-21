package ai.elimu.web.content.letter_sound;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Word;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter-sound/edit")
@RequiredArgsConstructor
public class LetterSoundEditController {

  private final Logger logger = LogManager.getLogger();

  private final LetterSoundDao letterSoundDao;

  private final LetterSoundContributionEventDao letterSoundContributionEventDao;

  private final LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;

  private final LetterDao letterDao;

  private final SoundDao soundDao;

  private final WordDao wordDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(Model model, @PathVariable Long id) {
    logger.info("handleRequest");

    LetterSound letterSound = letterSoundDao.read(id);
    model.addAttribute("letterSound", letterSound);

    model.addAttribute("timeStart", System.currentTimeMillis());

    List<Letter> letters = letterDao.readAllOrdered();
    model.addAttribute("letters", letters);

    List<Sound> sounds = soundDao.readAllOrdered();
    model.addAttribute("sounds", sounds);

    model.addAttribute("letterSoundContributionEvents", letterSoundContributionEventDao.readAll(letterSound));
    model.addAttribute("letterSoundPeerReviewEvents", letterSoundPeerReviewEventDao.readAll(letterSound));

    List<Word> words = wordDao.readAllOrderedByUsage();
    model.addAttribute("words", words);

    return "content/letter-sound/edit";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
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
    if ((existingLetterSound != null) && !existingLetterSound.getId().equals(letterSound.getId())) {
      result.rejectValue("letters", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("letterSound", letterSound);

      model.addAttribute("timeStart", System.currentTimeMillis());

      List<Letter> letters = letterDao.readAllOrdered();
      model.addAttribute("letters", letters);

      List<Sound> sounds = soundDao.readAllOrdered();
      model.addAttribute("sounds", sounds);

      model.addAttribute("letterSoundContributionEvents", letterSoundContributionEventDao.readAll(letterSound));
      model.addAttribute("letterSoundPeerReviewEvents", letterSoundPeerReviewEventDao.readAll(letterSound));

      return "content/letter-sound/edit";
    } else {
      letterSound.setTimeLastUpdate(Calendar.getInstance());
      letterSound.setRevisionNumber(letterSound.getRevisionNumber() + 1);
      letterSoundDao.update(letterSound);

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
            "Letter-sound correspondence edited: " + contentUrl,
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
