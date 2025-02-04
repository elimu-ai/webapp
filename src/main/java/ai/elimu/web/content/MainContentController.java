package ai.elimu.web.content;

import ai.elimu.dao.AudioContributionEventDao;
import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.contributor.Contributor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content")
public class MainContentController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private SoundDao soundDao;

    @Autowired
    private LetterSoundDao letterSoundDao;

    @Autowired
    private NumberDao numberDao;

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

        model.addAttribute("letterCount", letterDao.readCount());
        model.addAttribute("soundCount", soundDao.readCount());
        model.addAttribute("letterSoundCount", letterSoundDao.readCount());
        model.addAttribute("numberCount", numberDao.readCount());
        model.addAttribute("syllableCount", syllableDao.readCount());
        model.addAttribute("wordCount", wordDao.readCount());
        model.addAttribute("emojiCount", emojiDao.readCount());
        model.addAttribute("storyBookCount", storyBookDao.readCount());
        model.addAttribute("audioCount", audioDao.readCount());
        model.addAttribute("imageCount", imageDao.readCount());
        model.addAttribute("videoCount", videoDao.readCount());

        return "content/main";
    }
}
