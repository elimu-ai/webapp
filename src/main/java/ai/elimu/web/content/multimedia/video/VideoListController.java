package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/multimedia/video/list")
@RequiredArgsConstructor
public class VideoListController {

  private final Logger logger = LogManager.getLogger();
    
  private final VideoDao videoDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<Video> videos = videoDao.readAllOrdered();
    model.addAttribute("videos", videos);

    return "content/multimedia/video/list";
  }
}
