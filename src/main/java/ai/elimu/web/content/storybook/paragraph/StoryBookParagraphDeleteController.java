package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.AudioDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Role;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/paragraph/delete")
@RequiredArgsConstructor
@Slf4j
public class StoryBookParagraphDeleteController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final AudioDao audioDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(HttpSession session, @PathVariable Long id) {
    log.info("handleRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    log.info("contributor.getRoles(): " + contributor.getRoles());
    if (!contributor.getRoles().contains(Role.EDITOR)) {
      // TODO: return HttpStatus.FORBIDDEN
      throw new IllegalAccessError("Missing role for access");
    }

    StoryBookParagraph storyBookParagraphToBeDeleted = storyBookParagraphDao.read(id);
    log.info("storyBookParagraphToBeDeleted: " + storyBookParagraphToBeDeleted);
    log.info("storyBookParagraphToBeDeleted.getSortOrder(): " + storyBookParagraphToBeDeleted.getSortOrder());

    String paragraphTextBeforeDeletion = storyBookParagraphToBeDeleted.getOriginalText();

    // Delete the paragraph's reference from corresponding audios (if any)
    List<Audio> paragraphAudios = audioDao.readAll(storyBookParagraphToBeDeleted);
    for (Audio paragraphAudio : paragraphAudios) {
      paragraphAudio.setStoryBookParagraph(null);
      audioDao.update(paragraphAudio);
    }

    // Delete the paragraph
    log.info("Deleting StoryBookParagraph with ID " + storyBookParagraphToBeDeleted.getId());
    storyBookParagraphDao.delete(storyBookParagraphToBeDeleted);

    // Update the storybook's metadata
    StoryBook storyBook = storyBookParagraphToBeDeleted.getStoryBookChapter().getStoryBook();
    storyBook.setTimeLastUpdate(Calendar.getInstance());
    storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
    storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
    storyBookDao.update(storyBook);

    // Store contribution event
    StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
    storyBookContributionEvent.setContributor(contributor);
    storyBookContributionEvent.setTimestamp(Calendar.getInstance());
    storyBookContributionEvent.setStoryBook(storyBook);
    storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
    storyBookContributionEvent.setComment("Deleted storybook paragraph in chapter " + (storyBookParagraphToBeDeleted.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
    storyBookContributionEvent.setParagraphTextBefore(paragraphTextBeforeDeletion);
    storyBookContributionEvent.setTimeSpentMs(0L);
    storyBookContributionEventDao.create(storyBookContributionEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.sendChannelMessage(
          "Storybook paragraph deleted: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );
    }

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

    // Refresh the REST API cache
    storyBooksJsonService.refreshStoryBooksJSONArray();

    return "redirect:/content/storybook/edit/" + storyBook.getId() + "#ch-id-" + storyBookParagraphToBeDeleted.getStoryBookChapter().getId();
  }
}
