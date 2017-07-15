package ai.elimu.web.content.multimedia.audio;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.multimedia.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/audio/list")
public class AudioListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AudioDao audioDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<Audio> audios = audioDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("audios", audios);

        return "content/multimedia/audio/list";
    }
}
