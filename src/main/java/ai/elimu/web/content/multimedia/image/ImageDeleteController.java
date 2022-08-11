package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.*;
import ai.elimu.model.content.multimedia.Image;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;

@Controller
@RequestMapping("/content/multimedia/image/delete")
public class ImageDeleteController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ImageDao imageDao;

    @Autowired
    private StoryBookDao storyBookDao;

    @Autowired
    private ImageComponent imageComponent;

    @Autowired
    private ImageContributionEventDao imageContributionEventDao;

    @Autowired
    private StoryBookChapterDao storyBookChapterDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        // Before deleting, check if the image is used in any StoryBooks
        Image image = imageDao.read(id);
        if (storyBookDao.readAllWithImage(image).size() > 0 || storyBookChapterDao.readAllWithImage(image).size() > 0) {
            imageComponent.setImageModel(model, image);
            model.addAttribute("errorCode", "StoryBookContainImage");

            return "content/multimedia/image/edit";
        }

        // Before deleting, remove any labels
        image.setLetters(new HashSet<>());
        image.setWords(new HashSet<>());
        image.setNumbers(new HashSet<>());
        imageDao.update(image);

        // Delete all contribution event connected with image
        imageContributionEventDao.deleteAllEventsForImage(image);

        // Delete the image from the database
        imageDao.delete(image);

        // Store contribution event
        // TODO

        return "redirect:/content/multimedia/image/list";
    }
}
