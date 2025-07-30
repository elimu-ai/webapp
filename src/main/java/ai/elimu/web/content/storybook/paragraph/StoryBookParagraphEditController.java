package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/paragraph/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookParagraphEditController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    log.info("handleRequest");

    StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(id);
    log.debug("storyBookParagraph: " + storyBookParagraph);
    model.addAttribute("storyBookParagraph", storyBookParagraph);

    return "content/storybook/paragraph/edit";
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
      return "content/storybook/paragraph/edit";
    } else {
      // Fetch previously stored paragraph to make it possible to check if the text was modified or not when
      // storing the StoryBookContributionEvent below.
      StoryBookParagraph storyBookParagraphBeforeEdit = storyBookParagraphDao.read(storyBookParagraph.getId());

      storyBookParagraphDao.update(storyBookParagraph);

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
      storyBookContributionEvent.setComment("Edited storybook paragraph in chapter " + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
      if (!storyBookParagraphBeforeEdit.getOriginalText().equals(storyBookParagraph.getOriginalText())) {
        storyBookContributionEvent.setParagraphTextBefore(StringUtils.abbreviate(storyBookParagraphBeforeEdit.getOriginalText(), 1000));
        storyBookContributionEvent.setParagraphTextAfter(StringUtils.abbreviate(storyBookParagraph.getOriginalText(), 1000));
      }
      storyBookContributionEventDao.create(storyBookContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Storybook paragraph edited: " + contentUrl,
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
