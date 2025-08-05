package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.entity.enums.Role;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/paragraph/delete/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookParagraphDeleteController {

  private final StoryBookDao storyBookDao;
  private final StoryBookContributionEventDao storyBookContributionEventDao;
  private final StoryBookParagraphDao storyBookParagraphDao;
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

    StoryBookParagraph storyBookParagraphToBeDeleted = storyBookParagraphDao.read(id);
    log.info("storyBookParagraphToBeDeleted: " + storyBookParagraphToBeDeleted);
    log.info("storyBookParagraphToBeDeleted.getSortOrder(): " + storyBookParagraphToBeDeleted.getSortOrder());

    String paragraphTextBeforeDeletion = storyBookParagraphToBeDeleted.getOriginalText();

    // Delete the paragraph
    log.info("Deleting StoryBookParagraph with ID " + storyBookParagraphToBeDeleted.getId());
    storyBookParagraphDao.delete(storyBookParagraphToBeDeleted);

    // Update the sorting order of the remaining paragraphs
    List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookParagraphToBeDeleted.getStoryBookChapter());
    log.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
    for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
      log.info("storyBookParagraph.getId(): " + storyBookParagraph.getId() + ", storyBookParagraph.getSortOrder(): " + storyBookParagraph.getSortOrder());
      if (storyBookParagraph.getSortOrder() > storyBookParagraphToBeDeleted.getSortOrder()) {
        // Reduce sort order by 1
        storyBookParagraph.setSortOrder(storyBookParagraph.getSortOrder() - 1);
        storyBookParagraphDao.update(storyBookParagraph);
        log.info("storyBookParagraph.getSortOrder() (after update): " + storyBookParagraph.getSortOrder());
      }
    }

    // Update the storybook's metadata
    StoryBook storyBook = storyBookParagraphToBeDeleted.getStoryBookChapter().getStoryBook();
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
    storyBookContributionEvent.setComment("Deleted storybook paragraph in chapter " + (storyBookParagraphToBeDeleted.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
    storyBookContributionEvent.setParagraphTextBefore(paragraphTextBeforeDeletion);
    storyBookContributionEventDao.create(storyBookContributionEvent);

    String contentUrl = DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId();
    String embedThumbnailUrl = null;
    if (storyBook.getCoverImage() != null) {
      embedThumbnailUrl = storyBook.getCoverImage().getUrl();
    }
    DiscordHelper.postToChannel(
        Channel.CONTENT,
        "Storybook paragraph deleted: " + contentUrl,
        "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
        "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
        null,
        embedThumbnailUrl
    );

    return "redirect:/content/storybook/edit/" + storyBook.getId() + "#ch-id-" + storyBookParagraphToBeDeleted.getStoryBookChapter().getId();
  }
}
