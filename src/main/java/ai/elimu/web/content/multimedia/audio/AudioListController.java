package ai.elimu.web.content.multimedia.audio;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/audio/list")
@RequiredArgsConstructor
@Slf4j
public class AudioListController {

  private final AudioDao audioDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Audio> audios = audioDao.readAllOrderedByTimeLastUpdate();
    model.addAttribute("audios", audios);

    return "content/multimedia/audio/list";
  }
}
