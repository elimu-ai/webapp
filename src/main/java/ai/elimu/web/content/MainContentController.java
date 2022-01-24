package ai.elimu.web.content;

import ai.elimu.dao.AudioContributionEventDao;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
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
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
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
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private NumberContributionEventDao numberContributionEventDao;
    
    @Autowired
    private ContributorDao contributorDao;

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
        } else if (StringUtils.isBlank(contributor.getMotivation()) && (EnvironmentContextLoaderListener.env != Environment.DEV)) {
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
        
        model.addAttribute("letterCount", letterDao.readCount());
        model.addAttribute("soundCount", soundDao.readCount());
        model.addAttribute("letterSoundCorrespondenceCount", letterSoundCorrespondenceDao.readCount());
        model.addAttribute("numberCount", numberDao.readCount());
        model.addAttribute("syllableCount", syllableDao.readCount());
        model.addAttribute("wordCount", wordDao.readCount());
        model.addAttribute("emojiCount", emojiDao.readCount());
        model.addAttribute("storyBookCount", storyBookDao.readCount());
        model.addAttribute("audioCount", audioDao.readCount());
        model.addAttribute("imageCount", imageDao.readCount());
        model.addAttribute("videoCount", videoDao.readCount());
        
        List<Contributor> contributorsWithStoryBookContributions = contributorDao.readAllWithStoryBookContributions();
        logger.info("contributorsWithStoryBookContributions.size(): " + contributorsWithStoryBookContributions.size());
        model.addAttribute("contributorsWithStoryBookContributions", contributorsWithStoryBookContributions);
        Map<Long, Long> storyBookContributionsCountMap = new HashMap<>();
        for (Contributor contributorWithContributions : contributorsWithStoryBookContributions) {
            storyBookContributionsCountMap.put(contributorWithContributions.getId(), storyBookContributionEventDao.readCount(contributorWithContributions));
        }
        model.addAttribute("storyBookContributionsCountMap", storyBookContributionsCountMap);
        
        List<Contributor> contributorsWithAudioContributions = contributorDao.readAllWithAudioContributions();
        logger.info("contributorsWithAudioContributions.size(): " + contributorsWithAudioContributions.size());
        model.addAttribute("contributorsWithAudioContributions", contributorsWithAudioContributions);
        Map<Long, Long> audioContributionsCountMap = new HashMap<>();
        for (Contributor contributorWithContributions : contributorsWithAudioContributions) {
            audioContributionsCountMap.put(contributorWithContributions.getId(), audioContributionEventDao.readCount(contributorWithContributions));
        }
        model.addAttribute("audioContributionsCountMap", audioContributionsCountMap);
        
        List<Contributor> contributorsWithWordContributions = contributorDao.readAllWithWordContributions();
        logger.info("contributorsWithWordContributions.size(): " + contributorsWithWordContributions.size());
        model.addAttribute("contributorsWithWordContributions", contributorsWithWordContributions);
        Map<Long, Long> wordContributionsCountMap = new HashMap<>();
        for (Contributor contributorWithContributions : contributorsWithWordContributions) {
            wordContributionsCountMap.put(contributorWithContributions.getId(), wordContributionEventDao.readCount(contributorWithContributions));
        }
        model.addAttribute("wordContributionsCountMap", wordContributionsCountMap);
        
        List<Contributor> contributorsWithNumberContributions = contributorDao.readAllWithNumberContributions();
        logger.info("contributorsWithNumberContributions.size(): " + contributorsWithNumberContributions.size());
        model.addAttribute("contributorsWithNumberContributions", contributorsWithNumberContributions);
        Map<Long, Long> numberContributionsCountMap = new HashMap<>();
        for (Contributor contributorWithContributions : contributorsWithNumberContributions) {
            numberContributionsCountMap.put(contributorWithContributions.getId(), numberContributionEventDao.readCount(contributorWithContributions));
        }
        model.addAttribute("numberContributionsCountMap", numberContributionsCountMap);
    	
        return "content/main";
    }
}
