package ai.elimu.web.content;

import ai.elimu.dao.AllophoneDao;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content")
public class MainContentController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private VideoDao videoDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            HttpServletRequest request, 
            HttpSession session, 
            Principal principal, 
            Model model) {
    	logger.info("handleRequest");
        
        // Check if the Contributor has not yet provided all required details
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        if (StringUtils.isBlank(contributor.getEmail())) {
            return "redirect:/content/contributor/add-email";
        } else if (StringUtils.isBlank(contributor.getFirstName()) || StringUtils.isBlank(contributor.getLastName())) {
            return "redirect:/content/contributor/edit-name";
        } else if (StringUtils.isBlank(contributor.getMotivation())) {
            return "redirect:/content/contributor/edit-motivation";
        } else {
            // Redirect to originally requested URL
            DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            logger.info("defaultSavedRequest: " + defaultSavedRequest);
            if (defaultSavedRequest != null) {
                logger.info("Redirecting to " + defaultSavedRequest.getServletPath());
                return "redirect:" + defaultSavedRequest.getServletPath();
            }
        }
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        model.addAttribute("allophoneCount", allophoneDao.readCount());
        model.addAttribute("numberCount", numberDao.readCount(language));
        model.addAttribute("letterCount", letterDao.readCount(language));
        model.addAttribute("syllableCount", syllableDao.readCount(language));
        model.addAttribute("wordCount", wordDao.readCount(language));
        model.addAttribute("emojiCount", emojiDao.readCount());
        model.addAttribute("storyBookCount", storyBookDao.readCount(language));
        model.addAttribute("audioCount", audioDao.readCount());
        model.addAttribute("imageCount", imageDao.readCount(language));
        model.addAttribute("videoCount", videoDao.readCount(language));
    	
        return "content/main";
    }
}
