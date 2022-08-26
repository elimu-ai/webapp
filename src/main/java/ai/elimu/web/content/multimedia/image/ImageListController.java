package ai.elimu.web.content.multimedia.image;

import java.util.List;

import ai.elimu.web.content.emoji.EmojiComponent;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.multimedia.Image;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/list")
public class ImageListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private WordDao wordDao;

    @Autowired
    private EmojiComponent emojiComponent;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Image> images = imageDao.readAllOrdered();
        model.addAttribute("images", images);
        
        model.addAttribute("emojisByWordId", emojiComponent.getEmojisByWordId());

        return "content/multimedia/image/list";
    }

}
