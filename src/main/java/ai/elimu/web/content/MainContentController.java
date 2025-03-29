package ai.elimu.web.content;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content")
@RequiredArgsConstructor
@Slf4j
public class MainContentController {

  private final LetterDao letterDao;

  private final SoundDao soundDao;

  private final LetterSoundDao letterSoundDao;

  private final NumberDao numberDao;

  private final SyllableDao syllableDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  private final StoryBookDao storyBookDao;

  private final ImageDao imageDao;

  private final VideoDao videoDao;

  @GetMapping
  public String handleRequest(
      HttpServletRequest request,
      HttpSession session,
      Principal principal,
      Model model) {
    log.info("handleRequest");

    model.addAttribute("letterCount", letterDao.readCount());
    model.addAttribute("soundCount", soundDao.readCount());
    model.addAttribute("letterSoundCount", letterSoundDao.readCount());
    model.addAttribute("numberCount", numberDao.readCount());
    model.addAttribute("syllableCount", syllableDao.readCount());
    model.addAttribute("wordCount", wordDao.readCount());
    model.addAttribute("emojiCount", emojiDao.readCount());
    model.addAttribute("storyBookCount", storyBookDao.readCount());
    model.addAttribute("imageCount", imageDao.readCount());
    model.addAttribute("videoCount", videoDao.readCount());

    return "content/main";
  }
}
