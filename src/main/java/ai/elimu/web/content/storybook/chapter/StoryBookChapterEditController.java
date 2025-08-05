package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookChapterEditController {

    private final StoryBookDao storyBookDao;
    private final StoryBookChapterDao storyBookChapterDao;
    private final StoryBookContributionEventDao storyBookContributionEventDao;

    private final ImageDao imageDao;

    private final StoryBooksJsonService storyBooksJsonService;

    @GetMapping
    public String handleRequest(Model model, @PathVariable Long storyBookId, @PathVariable Long id) {
        log.info("handleRequest");

        StoryBookChapter storyBookChapter = storyBookChapterDao.read(id);
        model.addAttribute("storyBookChapter", storyBookChapter);

        List<Image> images = imageDao.readAllOrdered();
        model.addAttribute("images", images);

        return "content/storybook/chapter/edit";
    }

    @PostMapping
    public String handleSubmit(
          HttpSession session,
          @PathVariable Long storyBookId,
          @Valid StoryBookChapter storyBookChapter,
          BindingResult result,
          Model model
    ) {
        log.info("handleSubmit");

        Contributor contributor = (Contributor) session.getAttribute("contributor");

        if (result.hasErrors()) {
            model.addAttribute("storyBookChapter", storyBookChapter);

            List<Image> images = imageDao.readAllOrdered();
            model.addAttribute("images", images);

            return "content/storybook/chapter/edit";
        } else {
            storyBookChapterDao.update(storyBookChapter);

            // Update the storybook's metadata
            StoryBook storyBook = storyBookChapter.getStoryBook();
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
            storyBookDao.update(storyBook);

            // Refresh the REST API cache
            storyBooksJsonService.refreshStoryBooksJSONArray();

            // Store contribution event
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor(contributor);
            storyBookContributionEvent.setTimestamp(Calendar.getInstance());
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookContributionEvent.setComment("Edited storybook chapter " + (storyBookChapter.getSortOrder() + 1) + " (🤖 auto-generated comment)");
            storyBookContributionEventDao.create(storyBookContributionEvent);

            DiscordHelper.postToChannel(Channel.CONTENT, "Storybook chapter edited: " + DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId() + "#ch-id-" + storyBookChapter.getId());

            return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
        }
    }
}
