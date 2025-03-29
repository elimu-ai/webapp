package ai.elimu.web.content.sound;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SoundContributionEventDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.SoundContributionEvent;
import ai.elimu.model.v2.enums.content.sound.SoundType;
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
@RequestMapping("/content/sound/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class SoundEditController {

  private final SoundDao soundDao;

  private final SoundContributionEventDao soundContributionEventDao;

  private final LetterSoundDao letterSoundDao;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

    Sound sound = soundDao.read(id);
    model.addAttribute("sound", sound);
    model.addAttribute("timeStart", System.currentTimeMillis());

    model.addAttribute("soundTypes", SoundType.values());

    model.addAttribute("soundContributionEvents", soundContributionEventDao.readAll(sound));

    model.addAttribute("letterSounds", letterSoundDao.readAll());

    return "content/sound/edit";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @PathVariable Long id,
      @Valid Sound sound,
      BindingResult result,
      Model model
  ) {
    log.info("handleSubmit");

    if (StringUtils.isNotBlank(sound.getValueIpa())) {
      Sound existingSound = soundDao.readByValueIpa(sound.getValueIpa());
      if ((existingSound != null) && !existingSound.getId().equals(sound.getId())) {
        result.rejectValue("valueIpa", "NonUnique");
      }
    }

    if (StringUtils.isNotBlank(sound.getValueSampa())) {
      Sound existingSound = soundDao.readByValueSampa(sound.getValueSampa());
      if ((existingSound != null) && !existingSound.getId().equals(sound.getId())) {
        result.rejectValue("valueSampa", "NonUnique");
      }
    }

    if (result.hasErrors()) {
      model.addAttribute("sound", sound);
      model.addAttribute("timeStart", System.currentTimeMillis());
      model.addAttribute("soundTypes", SoundType.values());
      model.addAttribute("soundContributionEvents", soundContributionEventDao.readAll(sound));
      model.addAttribute("letterSounds", letterSoundDao.readAll());
      return "content/sound/edit";
    } else {
      sound.setTimeLastUpdate(Calendar.getInstance());
      sound.setRevisionNumber(sound.getRevisionNumber() + 1);
      soundDao.update(sound);

      SoundContributionEvent soundContributionEvent = new SoundContributionEvent();
      soundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      soundContributionEvent.setTimestamp(Calendar.getInstance());
      soundContributionEvent.setSound(sound);
      soundContributionEvent.setRevisionNumber(sound.getRevisionNumber());
      soundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      soundContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      soundContributionEventDao.create(soundContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/sound/edit/" + sound.getId();
        DiscordHelper.sendChannelMessage(
            "Sound edited: " + contentUrl,
            "/" + soundContributionEvent.getSound().getValueIpa() + "/",
            "Comment: \"" + soundContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      return "redirect:/content/sound/list#" + sound.getId();
    }
  }
}
