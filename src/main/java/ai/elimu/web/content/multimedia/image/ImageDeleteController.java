package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/multimedia/image/delete/{id}")
@RequiredArgsConstructor
@Slf4j
public class ImageDeleteController {

  private final ImageDao imageDao;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

    // Before deleting, check if the image is used in any StoryBooks
    // TODO

    // Before deleting, remove any labels
    // TODO

    // Before deleting, remove any image contribution events
    // TODO

    // Delete the image from the database
    // TODO
//        Image image = imageDao.read(id);
//        imageDao.delete(image);

    return "redirect:/content/multimedia/image/list";
  }
}
