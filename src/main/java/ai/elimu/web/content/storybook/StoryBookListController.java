package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.v2.enums.ReadingLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/list")
@RequiredArgsConstructor
@Slf4j
public class StoryBookListController {

  private final StoryBookDao storyBookDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("storyBooksLevel1", storyBookDao.readAllOrdered(ReadingLevel.LEVEL1));
    model.addAttribute("storyBooksLevel2", storyBookDao.readAllOrdered(ReadingLevel.LEVEL2));
    model.addAttribute("storyBooksLevel3", storyBookDao.readAllOrdered(ReadingLevel.LEVEL3));
    model.addAttribute("storyBooksLevel4", storyBookDao.readAllOrdered(ReadingLevel.LEVEL4));
    model.addAttribute("storyBooksUnleveled", storyBookDao.readAllUnleveled());

    return "content/storybook/list";
  }
}
