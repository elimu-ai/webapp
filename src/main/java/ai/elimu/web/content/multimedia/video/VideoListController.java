package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/multimedia/video/list")
@RequiredArgsConstructor
@Slf4j
public class VideoListController {
    
  private final VideoDao videoDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Video> videos = videoDao.readAllOrdered();
    model.addAttribute("videos", videos);

    return "content/multimedia/video/list";
  }
}
