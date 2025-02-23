package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/delete")
@RequiredArgsConstructor
@Slf4j
public class ImageDeleteController {

  private final ImageDao imageDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

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
