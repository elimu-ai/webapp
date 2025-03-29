package ai.elimu.web.content.sound;

import ai.elimu.dao.SoundDao;
import ai.elimu.entity.content.Sound;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/sound/list")
@RequiredArgsConstructor
@Slf4j
public class SoundListController {

  private final SoundDao soundDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

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
