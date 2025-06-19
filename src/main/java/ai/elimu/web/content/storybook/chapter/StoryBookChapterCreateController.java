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
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/create")
@RequiredArgsConstructor
@Slf4j
public class StoryBookChapterCreateController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final ImageDao imageDao;

  @GetMapping
  public String handleRequest(
      @PathVariable Long storyBookId,
      Model model
  ) {
    log.info("handleRequest");

    StoryBookChapter storyBookChapter = new StoryBookChapter();

    StoryBook storyBook = storyBookDao.read(storyBookId);
    storyBookChapter.setStoryBook(storyBook);

    List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
    storyBookChapter.setSortOrder(storyBookChapters.size());

    model.addAttribute("storyBookChapter", storyBookChapter);

    List<Image> images = imageDao.readAllOrdered();
    model.addAttribute("images", images);

    return "content/storybook/chapter/create";
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

      return "content/storybook/chapter/create";
    } else {
      storyBookChapterDao.create(storyBookChapter);

      // Update the storybook's metadata
      StoryBook storyBook = storyBookChapter.getStoryBook();
      storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
      storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
      storyBookDao.update(storyBook);

      // Store contribution event
      StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
      storyBookContributionEvent.setContributor(contributor);
      storyBookContributionEvent.setTimestamp(Calendar.getInstance());
      storyBookContributionEvent.setStoryBook(storyBook);
      storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
      storyBookContributionEvent.setComment("Created storybook chapter " + (storyBookChapter.getSortOrder() + 1) + " (🤖 auto-generated comment)");
      storyBookContributionEventDao.create(storyBookContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.sendChannelMessage(
          "Storybook chapter created: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );

      return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
    }
  }
}
