package ai.elimu.web.content.multimedia.video;

import java.util.List;

import org.apache.log4j.Logger;
import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/video/list")
public class VideoListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private VideoDao videoDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Video> videos = videoDao.readAllOrdered(language);
        model.addAttribute("videos", videos);

        return "content/multimedia/video/list";
    }
}
