package org.literacyapp.web.content.multimedia.video;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.VideoDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.multimedia.Video;
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
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<Video> videos = videoDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("videos", videos);

        return "content/multimedia/video/list";
    }
}
