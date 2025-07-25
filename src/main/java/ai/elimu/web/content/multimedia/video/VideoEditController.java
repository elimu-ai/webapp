package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.VideoContributionEventDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.VideoContributionEvent;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.model.v2.enums.content.VideoFormat;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import ai.elimu.util.GitHubLfsHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/video/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class VideoEditController {

  private final VideoDao videoDao;

  private final VideoContributionEventDao videoContributionEventDao;

  private final LetterDao letterDao;

  private final NumberDao numberDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(
      Model model,
      @PathVariable Long id) {
    log.info("handleRequest");

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

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      Video video,
      @RequestParam("bytes") MultipartFile multipartFile,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    if (StringUtils.isBlank(video.getTitle())) {
      result.rejectValue("title", "NotNull");
    } else {
      Video existingVideo = videoDao.read(video.getTitle());
      if ((existingVideo != null) && !existingVideo.getId().equals(video.getId())) {
        result.rejectValue("title", "NonUnique");
      }
    }

    byte[] bytes = null;
    try {
      bytes = multipartFile.getBytes();
      if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
        result.rejectValue("bytes", "NotNull");
      } else {
        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName: " + originalFileName);
        if (originalFileName.toLowerCase().endsWith(".m4v")) {
          video.setVideoFormat(VideoFormat.M4V);
        } else if (originalFileName.toLowerCase().endsWith(".mp4")) {
          video.setVideoFormat(VideoFormat.MP4);
        } else {
          result.rejectValue("bytes", "typeMismatch");
        }

        if (video.getVideoFormat() != null) {
          String contentType = multipartFile.getContentType();
          log.info("contentType: " + contentType);
          video.setContentType(contentType);

          video.setFileSize(bytes.length);
          video.setChecksumMd5(ChecksumHelper.calculateMD5(bytes));

          // TODO: convert to a default video format?
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage());
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
      video.setRevisionNumber(video.getRevisionNumber() + 1);
      String checksumGitHub = GitHubLfsHelper.uploadVideoToLfs(video, bytes);
      video.setChecksumGitHub(checksumGitHub);
      videoDao.update(video);

      VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
      videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      videoContributionEvent.setTimestamp(Calendar.getInstance());
      videoContributionEvent.setVideo(video);
      videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
      videoContributionEventDao.create(videoContributionEvent);

      DiscordHelper.postToChannel(Channel.CONTENT, "Video updated: " + DomainHelper.getBaseUrl() + "/content/multimedia/video/edit/" + video.getId());

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
    log.info("initBinder");
    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
  }

  @PostMapping(value = "/add-content-label")
  @ResponseBody
  public String handleAddContentLabelRequest(
      HttpServletRequest request,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleAddContentLabelRequest");

    log.info("id: " + id);
    Video video = videoDao.read(id);

    String letterIdParameter = request.getParameter("letterId");
    log.info("letterIdParameter: " + letterIdParameter);
    if (StringUtils.isNotBlank(letterIdParameter)) {
      Long letterId = Long.valueOf(letterIdParameter);
      Letter letter = letterDao.read(letterId);
      Set<Letter> letters = video.getLetters();
      if (!letters.contains(letter)) {
        letters.add(letter);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);

        VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
        videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
        videoContributionEvent.setTimestamp(Calendar.getInstance());
        videoContributionEvent.setVideo(video);
        videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
        videoContributionEvent.setComment("Add letter label: \"" + letter.getText() + "\" (🤖 auto-generated comment)");
        videoContributionEventDao.create(videoContributionEvent);
      }
    }

    String numberIdParameter = request.getParameter("numberId");
    log.info("numberIdParameter: " + numberIdParameter);
    if (StringUtils.isNotBlank(numberIdParameter)) {
      Long numberId = Long.valueOf(numberIdParameter);
      Number number = numberDao.read(numberId);
      Set<Number> numbers = video.getNumbers();
      if (!numbers.contains(number)) {
        numbers.add(number);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);

        VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
        videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
        videoContributionEvent.setTimestamp(Calendar.getInstance());
        videoContributionEvent.setVideo(video);
        videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
        videoContributionEvent.setComment("Add number label: " + number.getValue() + " (🤖 auto-generated comment)");
        videoContributionEventDao.create(videoContributionEvent);
      }
    }

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = video.getWords();
      if (!words.contains(word)) {
        words.add(word);
        video.setRevisionNumber(video.getRevisionNumber() + 1);
        videoDao.update(video);

        VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
        videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
        videoContributionEvent.setTimestamp(Calendar.getInstance());
        videoContributionEvent.setVideo(video);
        videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
        videoContributionEvent.setComment("Add word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
        videoContributionEventDao.create(videoContributionEvent);
      }
    }

    return "success";
  }

  @PostMapping(value = "/remove-content-label")
  @ResponseBody
  public String handleRemoveContentLabelRequest(
      HttpServletRequest request,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleRemoveContentLabelRequest");

    log.info("id: " + id);
    Video video = videoDao.read(id);

    String letterIdParameter = request.getParameter("letterId");
    log.info("letterIdParameter: " + letterIdParameter);
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

      VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
      videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      videoContributionEvent.setTimestamp(Calendar.getInstance());
      videoContributionEvent.setVideo(video);
      videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
      videoContributionEvent.setComment("Remove letter label: \"" + letter.getText() + "\" (🤖 auto-generated comment)");
      videoContributionEventDao.create(videoContributionEvent);
    }

    String numberIdParameter = request.getParameter("numberId");
    log.info("numberIdParameter: " + numberIdParameter);
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

      VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
      videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      videoContributionEvent.setTimestamp(Calendar.getInstance());
      videoContributionEvent.setVideo(video);
      videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
      videoContributionEvent.setComment("Remove number label: " + number.getValue() + " (🤖 auto-generated comment)");
      videoContributionEventDao.create(videoContributionEvent);
    }

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
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

      VideoContributionEvent videoContributionEvent = new VideoContributionEvent();
      videoContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      videoContributionEvent.setTimestamp(Calendar.getInstance());
      videoContributionEvent.setVideo(video);
      videoContributionEvent.setRevisionNumber(video.getRevisionNumber());
      videoContributionEvent.setComment("Remove word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
      videoContributionEventDao.create(videoContributionEvent);
    }

    return "success";
  }

  private Map<Long, String> getEmojisByWordId() {
    log.info("getEmojisByWordId");

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
