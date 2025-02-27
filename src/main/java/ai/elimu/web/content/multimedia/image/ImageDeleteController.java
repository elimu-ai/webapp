package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/multimedia/image/delete/{id}")
@RequiredArgsConstructor
public class ImageDeleteController {

  private final Logger logger = LogManager.getLogger();

  private final ImageDao imageDao;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    logger.info("handleRequest");

    // Before deleting, check if the image is used in any StoryBooks
    // TODO

    // Before deleting, remove any labels
    // TODO

    // Delete the image from the database
    // TODO
//        Image image = imageDao.read(id);
//        imageDao.delete(image);

    // Store contribution event
    // TODO

    return "redirect:/content/multimedia/image/list";
  }
}
