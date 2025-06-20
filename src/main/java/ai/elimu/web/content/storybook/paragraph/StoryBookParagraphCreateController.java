package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/{storyBookChapterId}/paragraph/create")
@RequiredArgsConstructor
@Slf4j
public class StoryBookParagraphCreateController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long storyBookChapterId) {
    log.info("handleRequest");

    StoryBookParagraph storyBookParagraph = new StoryBookParagraph();

    StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
    storyBookParagraph.setStoryBookChapter(storyBookChapter);

    List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
    storyBookParagraph.setSortOrder(storyBookParagraphs.size());

    model.addAttribute("storyBookParagraph", storyBookParagraph);

    return "content/storybook/paragraph/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid StoryBookParagraph storyBookParagraph,
      BindingResult result,
      Model model
  ) {
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    if (result.hasErrors()) {
      model.addAttribute("storyBookParagraph", storyBookParagraph);
      return "content/storybook/paragraph/create";
    } else {
      storyBookParagraphDao.create(storyBookParagraph);

      // Update the storybook's metadata
      StoryBook storyBook = storyBookParagraph.getStoryBookChapter().getStoryBook();
      storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
      storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
      storyBookDao.update(storyBook);

      // Store contribution event
      StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
      storyBookContributionEvent.setContributor(contributor);
      storyBookContributionEvent.setTimestamp(Calendar.getInstance());
      storyBookContributionEvent.setStoryBook(storyBook);
      storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
      storyBookContributionEvent.setComment("Created storybook paragraph in chapter " + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
      storyBookContributionEventDao.create(storyBookContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Storybook paragraph created: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );

      // Refresh the REST API cache
      storyBooksJsonService.refreshStoryBooksJSONArray();

      return "redirect:/content/storybook/edit/" +
          storyBookParagraph.getStoryBookChapter().getStoryBook().getId() +
          "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
    }
  }
}
