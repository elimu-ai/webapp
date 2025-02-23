package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class StoryBookParagraphEditController {

  private final Logger logger = LogManager.getLogger();

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    logger.info("handleRequest");

    StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(id);
    logger.info("storyBookParagraph: " + storyBookParagraph);
    model.addAttribute("storyBookParagraph", storyBookParagraph);

    model.addAttribute("timeStart", System.currentTimeMillis());

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
    logger.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    if (result.hasErrors()) {
      model.addAttribute("storyBookParagraph", storyBookParagraph);
      model.addAttribute("timeStart", System.currentTimeMillis());
      return "content/storybook/paragraph/edit";
    } else {
      // Fetch previously stored paragraph to make it possible to check if the text was modified or not when
      // storing the StoryBookContributionEvent below.
      StoryBookParagraph storyBookParagraphBeforeEdit = storyBookParagraphDao.read(storyBookParagraph.getId());

      storyBookParagraphDao.update(storyBookParagraph);

      // Update the storybook's metadata
      StoryBook storyBook = storyBookParagraph.getStoryBookChapter().getStoryBook();
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
      storyBookContributionEvent.setComment("Edited storybook paragraph in chapter " + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
      if (!storyBookParagraphBeforeEdit.getOriginalText().equals(storyBookParagraph.getOriginalText())) {
        storyBookContributionEvent.setParagraphTextBefore(StringUtils.abbreviate(storyBookParagraphBeforeEdit.getOriginalText(), 1000));
        storyBookContributionEvent.setParagraphTextAfter(StringUtils.abbreviate(storyBookParagraph.getOriginalText(), 1000));
      }
      storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      storyBookContributionEventDao.create(storyBookContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
        String embedThumbnailUrl = null;
        if (storyBook.getCoverImage() != null) {
          embedThumbnailUrl = storyBook.getCoverImage().getUrl();
        }
        DiscordHelper.sendChannelMessage(
            "Storybook paragraph edited: " + contentUrl,
            "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
            "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
            null,
            embedThumbnailUrl
        );
      }

      // Refresh the REST API cache
      storyBooksJsonService.refreshStoryBooksJSONArray();

      return "redirect:/content/storybook/edit/" +
          storyBookParagraph.getStoryBookChapter().getStoryBook().getId() +
          "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
    }
  }
}
