package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.multimedia.Video;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

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
