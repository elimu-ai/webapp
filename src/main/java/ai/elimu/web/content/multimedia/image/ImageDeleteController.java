package ai.elimu.web.content.multimedia.image;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/delete")
public class ImageDeleteController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
//        Image image = imageDao.read(id);
//        imageDao.delete(image);

        return "redirect:/content/multimedia/image/list";
    }
}
