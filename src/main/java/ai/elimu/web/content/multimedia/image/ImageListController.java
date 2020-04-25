package ai.elimu.web.content.multimedia.image;

import java.util.List;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/list")
public class ImageListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Image> images = imageDao.readAllOrdered();
        model.addAttribute("images", images);

        return "content/multimedia/image/list";
    }
}
