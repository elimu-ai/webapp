package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
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
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/move/{id}/{direction}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookChapterMoveController {

  private final StoryBookDao storyBookDao;
  private final StoryBookChapterDao storyBookChapterDao;
  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(HttpSession session, @PathVariable Long storyBookId, @PathVariable Long id, @PathVariable String direction) {
    log.info("handleRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    log.info("contributor.getRoles(): " + contributor.getRoles());
    if (!contributor.getRoles().contains(Role.EDITOR)) {
      // TODO: return HttpStatus.FORBIDDEN
      throw new IllegalAccessError("Missing role: " + Role.EDITOR);
    }

    StoryBookChapter storyBookChapter = storyBookChapterDao.read(id);
    log.info("storyBookChapter.getSortOrder(): " + storyBookChapter.getSortOrder());
    log.info("direction: " + direction);

    // Find the neighboring chapter to swap sort order with
    StoryBook storyBook = storyBookChapter.getStoryBook();
    List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
    int index = -1;
    for (int i = 0; i < storyBookChapters.size(); i++) {
      if (storyBookChapters.get(i).getId().equals(storyBookChapter.getId())) {
        index = i;
        break;
      }
    }
    int neighborIndex = "up".equals(direction) ? index - 1 : "down".equals(direction) ? index + 1 : -1;
    if ((index == -1) || (neighborIndex < 0) || (neighborIndex >= storyBookChapters.size())) {
      // Already at the top/bottom, chapter not found, or invalid direction: nothing to swap
      return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
    }
    StoryBookChapter neighborChapter = storyBookChapters.get(neighborIndex);

    // Swap the sorting order of the two chapters
    Integer sortOrder = storyBookChapter.getSortOrder();
    storyBookChapter.setSortOrder(neighborChapter.getSortOrder());
    neighborChapter.setSortOrder(sortOrder);
    storyBookChapterDao.update(storyBookChapter);
    storyBookChapterDao.update(neighborChapter);
    log.info("storyBookChapter.getSortOrder() (after update): " + storyBookChapter.getSortOrder());

    // Update the StoryBook's metadata
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
    storyBookContributionEvent.setComment("Moved storybook chapter " + (storyBookChapter.getSortOrder() + 1) + "/" + storyBookChapters.size() + " " + direction + " (🤖 auto-generated comment)");
    storyBookContributionEventDao.create(storyBookContributionEvent);

    DiscordHelper.postToChannel(Channel.CONTENT, "Storybook chapter moved: " + DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId() + "#ch-id-" + storyBookChapter.getId());

    return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
  }
}
