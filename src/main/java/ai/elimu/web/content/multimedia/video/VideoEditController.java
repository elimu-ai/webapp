package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.model.v2.enums.content.VideoFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/video/edit")
@RequiredArgsConstructor
public class VideoEditController {

  private final Logger logger = LogManager.getLogger();

  private final VideoDao videoDao;

  private final LetterDao letterDao;

  private final NumberDao numberDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(
      Model model,
      @PathVariable Long id) {
    logger.info("handleRequest");

    Video video = videoDao.read(id);
    model.addAttribute("video", video);

    model.addAttribute("contentLicenses", ContentLicense.values());

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    model.addAttribute("letters", letterDao.readAllOrdered());
    model.addAttribute("numbers", numberDao.readAllOrdered());
    model.addAttribute("words", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/multimedia/video/edit";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String handleSubmit(
      Video video,
      @RequestParam("bytes") MultipartFile multipartFile,
      BindingResult result,
      Model model) {
    logger.info("handleSubmit");

    if (StringUtils.isBlank(video.getTitle())) {
      result.rejectValue("title", "NotNull");
    } else {
      Video existingVideo = videoDao.read(video.getTitle());
      if ((existingVideo != null) && !existingVideo.getId().equals(video.getId())) {
        result.rejectValue("title", "NonUnique");
      }
    }

    try {
      byte[] bytes = multipartFile.getBytes();
      if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
        result.rejectValue("bytes", "NotNull");
      } else {
        String originalFileName = multipartFile.getOriginalFilename();
        logger.info("originalFileName: " + originalFileName);
        if (originalFileName.toLowerCase().endsWith(".m4v")) {
          video.setVideoFormat(VideoFormat.M4V);
        } else if (originalFileName.toLowerCase().endsWith(".mp4")) {
          video.setVideoFormat(VideoFormat.MP4);
        } else {
          result.rejectValue("bytes", "typeMismatch");
        }

        if (video.getVideoFormat() != null) {
          String contentType = multipartFile.getContentType();
          logger.info("contentType: " + contentType);
          video.setContentType(contentType);

          video.setBytes(bytes);

          // TODO: convert to a default video format?
        }
      }
    } catch (IOException e) {
      logger.error(e);
    }

    if (result.hasErrors()) {
      model.addAttribute("video", video);
      model.addAttribute("contentLicenses", ContentLicense.values());
      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());
      model.addAttribute("letters", letterDao.readAllOrdered());
      model.addAttribute("numbers", numberDao.readAllOrdered());
      model.addAttribute("words", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());
      return "content/multimedia/video/edit";
    } else {
      video.setTitle(video.getTitle().toLowerCase());
      video.setTimeLastUpdate(Calendar.getInstance());
      video.setRevisionNumber(video.getRevisionNumber() + 1);
      videoDao.update(video);

      return "redirect:/content/multimedia/video/list#" + video.getId();
    }
  }

  /**
   * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
   * <p></p>
   * Fixes this error message: "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property
   * 'bytes[0]'"
   */
  @InitBinder
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    logger.info("initBinder");
    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
  }

  @RequestMapping(value = "/{id}/add-content-label", method = RequestMethod.POST)
  @ResponseBody
  public String handleAddContentLabelRequest(
      HttpServletRequest request,
      @PathVariable Long id) {
    logger.info("handleAddContentLabelRequest");

    logger.info("id: " + id);
    Video video = videoDao.read(id);

    String letterIdParameter = request.getParameter("letterId");
    logger.info("letterIdParameter: " + letterIdParameter);
    if (StringUtils.isNotBlank(letterIdParameter)) {
      Long letterId = Long.valueOf(letterIdParameter);
      Letter letter = letterDao.read(letterId);
      Set<Letter> letters = video.getLetters();
      if (!letters.contains(letter)) {
        letters.add(letter);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);
      }
    }

    String numberIdParameter = request.getParameter("numberId");
    logger.info("numberIdParameter: " + numberIdParameter);
    if (StringUtils.isNotBlank(numberIdParameter)) {
      Long numberId = Long.valueOf(numberIdParameter);
      Number number = numberDao.read(numberId);
      Set<Number> numbers = video.getNumbers();
      if (!numbers.contains(number)) {
        numbers.add(number);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);
      }
    }

    String wordIdParameter = request.getParameter("wordId");
    logger.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = video.getWords();
      if (!words.contains(word)) {
        words.add(word);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);
      }
    }

    return "success";
  }

  @RequestMapping(value = "/{id}/remove-content-label", method = RequestMethod.POST)
  @ResponseBody
  public String handleRemoveContentLabelRequest(
      HttpServletRequest request,
      @PathVariable Long id) {
    logger.info("handleRemoveContentLabelRequest");

    logger.info("id: " + id);
    Video video = videoDao.read(id);

    String letterIdParameter = request.getParameter("letterId");
    logger.info("letterIdParameter: " + letterIdParameter);
    if (StringUtils.isNotBlank(letterIdParameter)) {
      Long letterId = Long.valueOf(letterIdParameter);
      Letter letter = letterDao.read(letterId);
      Set<Letter> letters = video.getLetters();
      Iterator<Letter> iterator = letters.iterator();
      while (iterator.hasNext()) {
        Letter existingLetter = iterator.next();
        if (existingLetter.getId().equals(letter.getId())) {
          iterator.remove();
        }
      }
      video.setRevisionNumber(video.getRevisionNumber() + 1);
      videoDao.update(video);
    }

    String numberIdParameter = request.getParameter("numberId");
    logger.info("numberIdParameter: " + numberIdParameter);
    if (StringUtils.isNotBlank(numberIdParameter)) {
      Long numberId = Long.valueOf(numberIdParameter);
      Number number = numberDao.read(numberId);
      Set<Number> numbers = video.getNumbers();
      Iterator<Number> iterator = numbers.iterator();
      while (iterator.hasNext()) {
        Number existingNumber = iterator.next();
        if (existingNumber.getId().equals(number.getId())) {
          iterator.remove();
        }
      }
      video.setRevisionNumber(video.getRevisionNumber() + 1);
      videoDao.update(video);
    }

    String wordIdParameter = request.getParameter("wordId");
    logger.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = video.getWords();
      Iterator<Word> iterator = words.iterator();
      while (iterator.hasNext()) {
        Word existingWord = iterator.next();
        if (existingWord.getId().equals(word.getId())) {
          iterator.remove();
        }
      }
      video.setRevisionNumber(video.getRevisionNumber() + 1);
      videoDao.update(video);
    }

    return "success";
  }

  private Map<Long, String> getEmojisByWordId() {
    logger.info("getEmojisByWordId");

    Map<Long, String> emojisByWordId = new HashMap<>();

    for (Word word : wordDao.readAll()) {
      String emojiGlyphs = "";

      List<Emoji> emojis = emojiDao.readAllLabeled(word);
      for (Emoji emoji : emojis) {
        emojiGlyphs += emoji.getGlyph();
      }

      if (StringUtils.isNotBlank(emojiGlyphs)) {
        emojisByWordId.put(word.getId(), emojiGlyphs);
      }
    }

    return emojisByWordId;
  }
}
