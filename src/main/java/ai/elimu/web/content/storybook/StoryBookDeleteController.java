package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import ai.elimu.entity.enums.Role;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/delete/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookDeleteController {

    private final StoryBookDao storyBookDao;
    private final StoryBookChapterDao storyBookChapterDao;
    private final StoryBookParagraphDao storyBookParagraphDao;
    private final StoryBookContributionEventDao storyBookContributionEventDao;
    private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

    private final StoryBookLearningEventDao storyBookLearningEventDao;

    private final StoryBooksJsonService storyBooksJsonService;

    @GetMapping
    public String handleRequest(HttpSession session, @PathVariable Long id) {
        log.info("handleRequest");

        Contributor contributor = (Contributor) session.getAttribute("contributor");
        log.info("contributor.getRoles(): " + contributor.getRoles());
        if (!contributor.getRoles().contains(Role.EDITOR)) {
            // TODO: return HttpStatus.FORBIDDEN
            throw new IllegalAccessError("Missing role: " + Role.EDITOR);
        }

        StoryBook storyBook = storyBookDao.read(id);
        log.info("storyBook: " + storyBook);

        // Delete peer-review events
        List<StoryBookPeerReviewEvent> storyBookPeerReviewEvents = storyBookPeerReviewEventDao.readAll(storyBook);
        log.info("storyBookPeerReviewEvents.size(): " + storyBookPeerReviewEvents.size());
        for (StoryBookPeerReviewEvent storyBookPeerReviewEvent : storyBookPeerReviewEvents) {
            storyBookPeerReviewEventDao.delete(storyBookPeerReviewEvent);
        }

        // Delete contribution events
        List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(storyBook);
        log.info("storyBookContributionEvents.size(): " + storyBookContributionEvents.size());
        for (StoryBookContributionEvent storyBookContributionEvent : storyBookContributionEvents) {
            storyBookContributionEventDao.delete(storyBookContributionEvent);
        }

        // Delete paragraphs
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBook);
        log.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
            storyBookParagraphDao.delete(storyBookParagraph);
        }

        // Delete chapters
        List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
        log.info("storyBookChapters.size(): " + storyBookChapters.size());
        for (StoryBookChapter storyBookChapter : storyBookChapters) {
            storyBookChapterDao.delete(storyBookChapter);
        }

        // Remove from storybook learning events
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAll(storyBook);
        log.info("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
        for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
            storyBookLearningEvent.setStoryBook(null);
            storyBookLearningEventDao.update(storyBookLearningEvent);
        }

        // Delete storybook
        storyBookDao.delete(storyBook);
        
        DiscordHelper.postToChannel(Channel.CONTENT, "Storybook deleted: \"" + storyBook.getTitle() + "\" (ID " + storyBook.getId() + ")");

        // Refresh the REST API cache
        storyBooksJsonService.refreshStoryBooksJSONArray();

        return "redirect:/content/storybook/list";
    }
}
