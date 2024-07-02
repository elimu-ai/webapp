package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.model.content.StoryBookChapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter")
public class StoryBookChapterController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private StoryBookChapterDao storyBookChapterDao;

    @Autowired
    private StoryBookChapterComponent storyBookChapterComponent;

    @RequestMapping(value = "/{storyBookChapterId}/deleteImage", method = RequestMethod.GET)
    public String deleteImageFormStoryBookChapter(HttpSession session, @PathVariable Long storyBookId, @PathVariable Long storyBookChapterId) {
        logger.info("deleteImageFormStoryBookChapter");
        storyBookChapterComponent.checkEditorRole(session);

        StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
        storyBookChapter.setImage(null);
        storyBookChapterDao.update(storyBookChapter);

        return "redirect:/content/storybook/edit/" + storyBookId;
    }

}
