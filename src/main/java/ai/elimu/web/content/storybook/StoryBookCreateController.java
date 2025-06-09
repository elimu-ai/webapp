package ai.elimu.web.content.storybook;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/create")
@RequiredArgsConstructor
@Slf4j
public class StoryBookCreateController {

  private final StoryBookDao storybookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final ImageDao imageDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    StoryBook storyBook = new StoryBook();
    model.addAttribute("storyBook", storyBook);

    model.addAttribute("contentLicenses", ContentLicense.values());

    List<Image> coverImages = imageDao.readAllOrdered();
    model.addAttribute("coverImages", coverImages);

    model.addAttribute("readingLevels", ReadingLevel.values());

    return "content/storybook/create";
  }

  @PostMapping
  public String handleSubmit(
      @Valid StoryBook storyBook,
      BindingResult result,
      Model model,
      HttpServletRequest request,
      HttpSession session) {
    log.info("handleSubmit");

    StoryBook existingStoryBook = storybookDao.readByTitle(storyBook.getTitle());
    if (existingStoryBook != null) {
      result.rejectValue("title", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("storybook", storyBook);

      model.addAttribute("contentLicenses", ContentLicense.values());

      List<Image> coverImages = imageDao.readAllOrdered();
      model.addAttribute("coverImages", coverImages);

      model.addAttribute("readingLevels", ReadingLevel.values());

      return "content/storybook/create";
    } else {
      storybookDao.create(storyBook);

      StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
      storyBookContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      storyBookContributionEvent.setTimestamp(Calendar.getInstance());
      storyBookContributionEvent.setStoryBook(storyBook);
      storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
      storyBookContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      storyBookContributionEventDao.create(storyBookContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
        String embedThumbnailUrl = null;
        if (storyBook.getCoverImage() != null) {
          embedThumbnailUrl = storyBook.getCoverImage().getUrl();
        }
        DiscordHelper.sendChannelMessage(
            "Storybook created: " + contentUrl,
            "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
            "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
            null,
            embedThumbnailUrl
        );
      }

      return "redirect:/content/storybook/list#" + storyBook.getId();
    }
  }
}
