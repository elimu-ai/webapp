package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/delete/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookChapterDeleteController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final ImageDao imageDao;

  private final ImageContributionEventDao imageContributionEventDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(HttpSession session, @PathVariable Long storyBookId, @PathVariable Long id) {
    log.info("handleRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    log.info("contributor.getRoles(): " + contributor.getRoles());
    if (!contributor.getRoles().contains(Role.EDITOR)) {
      // TODO: return HttpStatus.FORBIDDEN
      throw new IllegalAccessError("Missing role for access");
    }

    StoryBookChapter storyBookChapterToBeDeleted = storyBookChapterDao.read(id);
    log.info("storyBookChapterToBeDeleted: " + storyBookChapterToBeDeleted);
    log.info("storyBookChapterToBeDeleted.getSortOrder(): " + storyBookChapterToBeDeleted.getSortOrder());

    // Delete the chapter's paragraphs
    List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapterToBeDeleted);
    log.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
    for (StoryBookParagraph storyBookParagraphToBeDeleted : storyBookParagraphs) {
      log.info("Deleting StoryBookParagraph with ID " + storyBookParagraphToBeDeleted.getId());
      storyBookParagraphDao.delete(storyBookParagraphToBeDeleted);
    }

    // Delete the chapter
    log.info("Deleting StoryBookChapter with ID " + storyBookChapterToBeDeleted.getId());
    storyBookChapterDao.delete(storyBookChapterToBeDeleted);

    // Delete the chapter's image (if any)
    Image chapterImage = storyBookChapterToBeDeleted.getImage();
    log.info("chapterImage: " + chapterImage);
    if (chapterImage != null) {
      // Remove content labels
      chapterImage.setLiteracySkills(null);
      chapterImage.setNumeracySkills(null);
      chapterImage.setLetters(null);
      chapterImage.setNumbers(null);
      chapterImage.setWords(null);
      imageDao.update(chapterImage);

      // Remove contribution events
      for (ImageContributionEvent imageContributionEvent : imageContributionEventDao.readAll(chapterImage)) {
        log.warn("Deleting ImageContributionEvent from the database");
        imageContributionEventDao.delete(imageContributionEvent);
      }

      log.warn("Deleting the chapter image from the database");
      imageDao.delete(chapterImage);
    }

    // Update the StoryBook's metadata
    StoryBook storyBook = storyBookChapterToBeDeleted.getStoryBook();
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
    storyBookContributionEvent.setComment("Deleted storybook chapter " + (storyBookChapterToBeDeleted.getSortOrder() + 1) + " (🤖 auto-generated comment)");
    storyBookContributionEvent.setTimeSpentMs(0L);
    storyBookContributionEventDao.create(storyBookContributionEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.sendChannelMessage(
          "Storybook chapter deleted: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );
    }

    // Update the sorting order of the remaining chapters
    List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
    log.info("storyBookChapters.size(): " + storyBookChapters.size());
    for (StoryBookChapter storyBookChapter : storyBookChapters) {
      log.info("storyBookChapter.getId(): " + storyBookChapter.getId() + ", storyBookChapter.getSortOrder(): " + storyBookChapter.getSortOrder());
      if (storyBookChapter.getSortOrder() > storyBookChapterToBeDeleted.getSortOrder()) {
        // Reduce sort order by 1
        storyBookChapter.setSortOrder(storyBookChapter.getSortOrder() - 1);
        storyBookChapterDao.update(storyBookChapter);
        log.info("storyBookChapter.getSortOrder() (after update): " + storyBookChapter.getSortOrder());
      }
    }

    // Refresh the REST API cache
    storyBooksJsonService.refreshStoryBooksJSONArray();

    return "redirect:/content/storybook/edit/" + storyBookId;
  }
}
