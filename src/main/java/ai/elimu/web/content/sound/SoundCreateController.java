package ai.elimu.web.content.sound;

import ai.elimu.dao.SoundContributionEventDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.SoundContributionEvent;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import ai.elimu.util.DiscordHelper;
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
@RequestMapping("/content/sound/create")
@RequiredArgsConstructor
@Slf4j
public class SoundCreateController {

  private final SoundDao soundDao;

  private final SoundContributionEventDao soundContributionEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    Sound sound = new Sound();
    model.addAttribute("sound", sound);

    model.addAttribute("soundTypes", SoundType.values());

    return "content/sound/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Sound sound,
      BindingResult result,
      Model model
  ) {
    log.info("handleSubmit");

    if (StringUtils.isNotBlank(sound.getValueIpa())) {
      Sound existingSound = soundDao.readByValueIpa(sound.getValueIpa());
      if (existingSound != null) {
        result.rejectValue("valueIpa", "NonUnique");
      }
    }

    if (StringUtils.isNotBlank(sound.getValueSampa())) {
      Sound existingSound = soundDao.readByValueSampa(sound.getValueSampa());
      if (existingSound != null) {
        result.rejectValue("valueSampa", "NonUnique");
      }
    }

    if (result.hasErrors()) {
      model.addAttribute("sound", sound);
      model.addAttribute("soundTypes", SoundType.values());
      return "content/sound/create";
    } else {
      soundDao.create(sound);

      SoundContributionEvent soundContributionEvent = new SoundContributionEvent();
      soundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      soundContributionEvent.setTimestamp(Calendar.getInstance());
      soundContributionEvent.setSound(sound);
      soundContributionEvent.setRevisionNumber(sound.getRevisionNumber());
      soundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      soundContributionEventDao.create(soundContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/sound/edit/" + sound.getId();
      DiscordHelper.sendChannelMessage(
          "Sound created: " + contentUrl,
          "/" + soundContributionEvent.getSound().getValueIpa() + "/",
          "Comment: \"" + soundContributionEvent.getComment() + "\"",
          null,
          null
      );

      return "redirect:/content/sound/list#" + sound.getId();
    }
  }
}
