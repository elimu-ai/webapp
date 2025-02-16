package ai.elimu.web.content.letter_sound;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.model.content.LetterSound;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/letter-sound/list")
@RequiredArgsConstructor
public class LetterSoundListController {

  private final Logger logger = LogManager.getLogger();

  private final LetterSoundDao letterSoundDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<LetterSound> letterSounds = letterSoundDao.readAllOrderedByUsage();
    model.addAttribute("letterSounds", letterSounds);

    int maxUsageCount = 0;
    for (LetterSound letterSound : letterSounds) {
      if (letterSound.getUsageCount() > maxUsageCount) {
        maxUsageCount = letterSound.getUsageCount();
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    return "content/letter-sound/list";
  }
}
