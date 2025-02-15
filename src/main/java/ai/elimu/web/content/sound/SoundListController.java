package ai.elimu.web.content.sound;

import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Sound;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/sound/list")
@RequiredArgsConstructor
public class SoundListController {

  private final Logger logger = LogManager.getLogger();

  private final SoundDao soundDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<Sound> sounds = soundDao.readAllOrderedByUsage();
    model.addAttribute("sounds", sounds);

    int maxUsageCount = 0;
    for (Sound sound : sounds) {
      if (sound.getUsageCount() > maxUsageCount) {
        maxUsageCount = sound.getUsageCount();
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    return "content/sound/list";
  }
}
